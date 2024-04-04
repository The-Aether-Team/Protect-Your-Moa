package com.aetherteam.protectyourmoa.capability.armor;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.inventory.menu.MoaInventoryMenu;
import com.aetherteam.protectyourmoa.item.combat.MoaArmorItem;
import com.aetherteam.protectyourmoa.network.ProtectPacketHandler;
import com.aetherteam.protectyourmoa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protectyourmoa.network.packet.clientbound.OpenMoaInventoryPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MoaArmorCapability implements MoaArmor, ContainerListener {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("F7EC75AF-718C-45BF-99E8-ADFF0BCAB659");

    private final Moa moa;

    private SimpleContainer inventory;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("equipSaddle", Triple.of(Type.BOOLEAN, (object) -> this.equipSaddle(), this::isSaddled)),
            Map.entry("equipArmor", Triple.of(Type.ITEM_STACK, (object) -> this.equipArmor((ItemStack) object), this::getArmor))
//            Map.entry("setArmor", Triple.of(Type.ITEM_STACK, (object) -> this.setArmor((ItemStack) object), this::getArmor)),
//            Map.entry("setChest", Triple.of(Type.BOOLEAN, (object) -> this.setChest((boolean) object), this::hasChest))
    );

    public MoaArmorCapability(Moa moa) {
        this.moa = moa;
        this.createInventory();
    }

    @Override
    public Moa getMoa() {
        return this.moa;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (!this.getInventory().getItem(0).isEmpty()) {
            tag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
        }
        if (!this.getInventory().getItem(1).isEmpty()) {
            tag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.of(tag.getCompound("SaddleItem"));
            if (itemStack.is(Items.SADDLE)) {
                this.getInventory().setItem(0, itemStack);
            }
        }
        if (tag.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(tag.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.getInventory().setItem(1, itemstack);
            }
        }
        this.updateContainerEquipment();
    }

    private void createInventory() {
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
//        this.updateContainerEquipment();
//        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    @Override
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

    private void updateContainerEquipment() {
        if (!this.getMoa().level().isClientSide()) {
            this.getMoa().setSaddled(!this.getInventory().getItem(0).isEmpty());
            this.setArmorEquipment(this.inventory.getItem(1));
            this.getMoa().setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

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

    @Override
    public void openInventory(ServerPlayer serverPlayer, Moa moa) {
        if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
            serverPlayer.closeContainer();
        }
        serverPlayer.nextContainerCounter();
        PacketRelay.sendToPlayer(ProtectPacketHandler.INSTANCE, new OpenMoaInventoryPacket(moa.getId(), inventory.getContainerSize(), serverPlayer.containerCounter), serverPlayer);
        serverPlayer.containerMenu = new MoaInventoryMenu(serverPlayer.containerCounter, serverPlayer.getInventory(), inventory, moa);
        serverPlayer.initMenu(serverPlayer.containerMenu);
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));
    }

    @Override
    public void equipSaddle() {
        this.getInventory().setItem(0, new ItemStack(Items.SADDLE));
    }

    @Override
    public boolean isSaddled() {
        return this.getMoa().isSaddled();
    }

    @Override
    public void equipArmor(ItemStack armor) {
        this.getInventory().setItem(1, armor.copyWithCount(1));
    }

    @Override
    public void setArmor(ItemStack itemStack) {
        this.getMoa().setItemSlot(EquipmentSlot.CHEST, itemStack);
        this.getMoa().setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    @Override
    public ItemStack getArmor() {
        return this.getMoa().getItemBySlot(EquipmentSlot.CHEST);
    }

    public boolean isWearingArmor() {
        return !this.getMoa().getItemBySlot(EquipmentSlot.CHEST).isEmpty();
    }

    //todo
    public boolean hasChest() {
        return false;
    }

    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    protected int getInventorySize() {
        return this.hasChest() ? 17 : 2;
    }

    public boolean isArmor(ItemStack stack) {
        return stack.getItem() instanceof MoaArmorItem;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new MoaArmorSyncPacket(this.getMoa().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return ProtectPacketHandler.INSTANCE;
    }
}
