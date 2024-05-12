package com.aetherteam.protect_your_moa.attachment;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.aetherteam.protect_your_moa.inventory.menu.MoaInventoryMenu;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import com.aetherteam.protect_your_moa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protect_your_moa.network.packet.clientbound.OpenMoaInventoryPacket;
import com.aetherteam.protect_your_moa.network.packet.clientbound.SetMoaChestPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
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
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MoaArmorAttachment implements INBTSynchable {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("F7EC75AF-718C-45BF-99E8-ADFF0BCAB659");

    private boolean chested;
    private SimpleContainer inventory;
    private boolean shouldSyncChest;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setShouldSyncChest", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncChest((boolean) object), this::shouldSyncChest))
    );

    /**
     * [CODE COPY] - {@link AbstractHorse#addAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.Horse#addAdditionalSaveData(CompoundTag)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.AbstractChestedHorse#addAdditionalSaveData(CompoundTag)}.
     */
    public static final Codec<MoaArmorAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("saddle_item").forGetter((moaArmorAttachment) -> moaArmorAttachment.getInventory().getItem(0)),
            ItemStack.CODEC.fieldOf("armor_item").forGetter((moaArmorAttachment) -> moaArmorAttachment.getInventory().getItem(1)),
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
    protected MoaArmorAttachment(ItemStack saddleItem, ItemStack armorItem, boolean chested, Map<Integer, ItemStack> inventory) { //todo test
        this.createInventory();
        if (saddleItem.is(Items.SADDLE)) {
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

    public void onJoinLevel(Moa moa) {
        if (!moa.level().isClientSide()) {
            this.updateContainerEquipment(moa); //todo verify this works
        } else {
            this.setSynched(moa.getId(), Direction.SERVER, "setShouldSyncChest", true);
        }
    }

    public void onUpdate(Moa moa) {
        this.syncChest(moa);
    }

    private void syncChest(Moa moa) {
        if (!moa.level().isClientSide()) {
            if (this.shouldSyncChest()) {
                this.setChest(this.hasChest());
                PacketRelay.sendToAll(new SetMoaChestPacket(moa.getId(), this.hasChest()));
                this.setShouldSyncChest(false);
            }
        }
    }

    /**
     * [CODE COPY] - {@link AbstractHorse#containerChanged(Container)}.<br><br>
     * [CODE COPY] - {@link net.minecraft.world.entity.animal.horse.Horse#containerChanged(Container)}.
     */
    public void setItem(Moa moa, int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
        ItemStack oldArmorStack = this.getArmor(moa);

        boolean oldSaddled = this.isSaddled(moa);
        this.updateContainerEquipment(moa);
        if (moa.tickCount > 20 && !oldSaddled && this.isSaddled(moa)) {
            moa.playSound(moa.getSaddleSoundEvent(), 0.5F, 1.0F);
        }

        ItemStack newArmorStack = this.getArmor(moa);
        if (moa.tickCount > 20 && this.isArmor(newArmorStack) && oldArmorStack != newArmorStack) {
            moa.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
    }


    /**
     * [CODE COPY] - {@link AbstractHorse#updateContainerEquipment()}.<br><br>
     * [CODE COPY] - {@link Horse#updateContainerEquipment()}.
     */
    private void updateContainerEquipment(Moa moa) {
        if (!moa.level().isClientSide()) {
            moa.setSaddled(!this.getInventory().getItem(0).isEmpty());
            this.setArmorEquipment(moa, this.inventory.getItem(1));
            moa.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    /**
     * [CODE COPY] - {@link Horse#setArmorEquipment(ItemStack)}.
     */
    private void setArmorEquipment(Moa moa, ItemStack stack) {
        this.setArmor(moa, stack);
        if (!moa.level().isClientSide()) {
            AttributeInstance armorAttribute = moa.getAttribute(Attributes.ARMOR);
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
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }
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

    public void equipSaddle(Moa moa) {
        this.setItem(moa, 0, new ItemStack(Items.SADDLE));
    }

    public boolean isSaddled(Moa moa) {
        return moa.isSaddled();
    }

    public void equipArmor(Moa moa, ItemStack armor) {
        this.setItem(moa, 1, armor.copyWithCount(1));
    }

    public void setArmor(Moa moa, ItemStack itemStack) {
        moa.setItemSlot(EquipmentSlot.CHEST, itemStack);
        moa.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    public ItemStack getArmor(Moa moa) {
        return moa.getItemBySlot(EquipmentSlot.CHEST);
    }

    public boolean isWearingArmor(Moa moa) {
        return !moa.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
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
