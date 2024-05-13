package com.aetherteam.protect_your_moa.attachment;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.inventory.menu.MoaInventoryMenu;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import com.aetherteam.protect_your_moa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protect_your_moa.network.packet.clientbound.OpenMoaInventoryPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MoaArmorAttachment implements INBTSynchable, ContainerListener {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("F7EC75AF-718C-45BF-99E8-ADFF0BCAB659");

    private Moa moa;
    private boolean chested;
    private SimpleContainer inventory;
    private boolean shouldSyncChest;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("equipSaddle", Triple.of(Type.BOOLEAN, (object) -> this.equipSaddle(), this::isSaddled)),
            Map.entry("equipArmor", Triple.of(Type.ITEM_STACK, (object) -> this.equipArmor((ItemStack) object), this::getArmor)),
            Map.entry("setChest", Triple.of(Type.BOOLEAN, (object) -> this.setChest((boolean) object), this::hasChest)),
            Map.entry("setShouldSyncChest", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncChest((boolean) object), this::shouldSyncChest))
    );

    /**
     * [CODE COPY] - {@link AbstractHorse#addAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.Horse#addAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.AbstractChestedHorse#addAdditionalSaveData(CompoundTag)}.
     */
    public static final Codec<MoaArmorAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.optionalFieldOf("saddle_item", ItemStack.EMPTY).forGetter((moaArmorAttachment) -> moaArmorAttachment.getInventory().getItem(0)),
            ItemStack.CODEC.optionalFieldOf("armor_item", ItemStack.EMPTY).forGetter((moaArmorAttachment) -> moaArmorAttachment.getInventory().getItem(1)),
            Codec.BOOL.fieldOf("chested").forGetter(MoaArmorAttachment::hasChest),
            ExtraCodecs.strictUnboundedMap(Codec.INT, ItemStack.CODEC).fieldOf("inventory").forGetter((moaArmorAttachment) -> {
                Map<Integer, ItemStack> inventory = new HashMap<>();
                if (moaArmorAttachment.hasChest()) {
                    for (int i = 2; i < moaArmorAttachment.inventory.getContainerSize(); ++i) {
                        ItemStack itemStack = moaArmorAttachment.inventory.getItem(i);
                        if (!itemStack.isEmpty()) {
                            inventory.put(i, itemStack);
                        }
                    }
                }
                ProtectYourMoa.LOGGER.info(String.valueOf(inventory));
                return inventory;
            })
    ).apply(instance, MoaArmorAttachment::new));

    public MoaArmorAttachment() {
        this.createInventory();
    }

    /**
     * [CODE COPY] - {@link AbstractHorse#readAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.Horse#readAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.AbstractChestedHorse#readAdditionalSaveData(CompoundTag)}.
     */
    protected MoaArmorAttachment(ItemStack saddleItem, ItemStack armorItem, boolean chested, Map<Integer, ItemStack> inventory) {
        this.createInventory();
        if (!saddleItem.isEmpty() && saddleItem.is(Items.SADDLE)) {
            this.getInventory().setItem(0, saddleItem);
        }
        if (!armorItem.isEmpty() && this.isArmor(armorItem)) {
            this.getInventory().setItem(1, armorItem);
        }
        this.setChest(chested);
        if (this.hasChest()) {
            for (Map.Entry<Integer, ItemStack> entry : inventory.entrySet()) {
                this.inventory.setItem(entry.getKey(), entry.getValue());
            }
        }
    }

    public Moa getMoa() {
        return this.moa;
    }

    public void onJoinLevel(Moa moa) {
        this.moa = moa;
        if (this.getMoa().level().isClientSide()) {
            this.setSynched(this.getMoa().getId(), Direction.SERVER, "setShouldSyncChest", true);
        }
    }

    public void onUpdate(Moa moa) {
        if (this.getMoa() == null) {
            this.moa = moa;
        }
        this.syncChest();
    }

    private void syncChest() {
        if (!this.getMoa().level().isClientSide()) {
            if (this.shouldSyncChest()) {
                this.setSynched(this.getMoa().getId(), Direction.CLIENT, "setChest", this.hasChest());
                this.setShouldSyncChest(false);
            }
        }
    }

    /**
     * [CODE COPY] - {@link AbstractHorse#containerChanged(Container)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.Horse#containerChanged(Container)}.
     */
    public void containerChanged(Container container) {
        ItemStack oldArmorStack = this.getArmor();

        boolean oldSaddled = this.isSaddled();
        this.updateContainerEquipment();
        if (this.getMoa().tickCount > 20 && !oldSaddled && this.isSaddled()) {
            this.getMoa().playSound(this.getMoa().getSaddleSoundEvent(), 0.5F, 1.0F);
        }

        ItemStack newArmorStack = this.getArmor();
        if (this.getMoa().tickCount > 20 && this.isArmor(newArmorStack) && oldArmorStack != newArmorStack) {
            this.getMoa().playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
    }

    /**
     * [CODE COPY] - {@link AbstractHorse#updateContainerEquipment()}.<br><br>
     * [CODE COPY] - {@link Horse#updateContainerEquipment()}.
     */
    private void updateContainerEquipment() {
        if (!this.getMoa().level().isClientSide()) {
            this.getMoa().setSaddled(!this.getInventory().getItem(0).isEmpty());
            this.setArmorEquipment(this.inventory.getItem(1));
            this.getMoa().setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    /**
     * [CODE COPY] - {@link Horse#setArmorEquipment(ItemStack)}.
     */
    private void setArmorEquipment(ItemStack stack) {
        this.setArmor(stack);
        if (!this.getMoa().level().isClientSide()) {
            AttributeInstance armorAttribute = this.getMoa().getAttribute(Attributes.ARMOR);
            if (armorAttribute != null) {
                armorAttribute.removeModifier(ARMOR_MODIFIER_UUID);
                if (this.isArmor(stack)) {
                    int protection = ((MoaArmorItem) stack.getItem()).getProtection();
                    if (protection != 0) {
                        armorAttribute.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", protection, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link AbstractHorse#createInventory()}.
     */
    public void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
    }

    /**
     * [CODE COPY] - {@link ServerPlayer#openHorseInventory(AbstractHorse, Container)}.
     */
    public void openInventory(ServerPlayer serverPlayer, Moa moa) {
        if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
            serverPlayer.closeContainer();
        }
        serverPlayer.nextContainerCounter();
        PacketRelay.sendToPlayer(new OpenMoaInventoryPacket(moa.getId(), inventory.getContainerSize(), serverPlayer.containerCounter), serverPlayer);
        serverPlayer.containerMenu = new MoaInventoryMenu(serverPlayer.containerCounter, serverPlayer.getInventory(), inventory, moa);
        serverPlayer.initMenu(serverPlayer.containerMenu);
        NeoForge.EVENT_BUS.post(new PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));
    }

    public void equipSaddle() {
        this.getInventory().setItem(0, new ItemStack(Items.SADDLE));
    }

    public boolean isSaddled() {
        return this.getMoa().isSaddled();
    }

    public void equipArmor(ItemStack armor) {
        this.getInventory().setItem(1, armor.copyWithCount(1));
    }

    public void setArmor(ItemStack itemStack) {
        this.getMoa().setItemSlot(EquipmentSlot.CHEST, itemStack);
        this.getMoa().setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    public ItemStack getArmor() {
        return this.getMoa().getItemBySlot(EquipmentSlot.CHEST);
    }

    public boolean isWearingArmor() {
        return !this.getMoa().getItemBySlot(EquipmentSlot.CHEST).isEmpty();
    }

    public void setChest(boolean chested) {
        this.chested = chested;
        this.createInventory();
    }

    public boolean hasChest() {
        return this.chested;
    }

    public void setShouldSyncChest(boolean sync) {
        this.shouldSyncChest = sync;
    }

    public boolean shouldSyncChest() {
        return this.shouldSyncChest;
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    protected int getInventorySize() {
        return this.hasChest() ? 17 : 2;
    }

    public boolean isArmor(ItemStack stack) {
        return stack.getItem() instanceof MoaArmorItem;
    }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public BasePacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new MoaArmorSyncPacket(entityID, key, type, value);
    }
}
