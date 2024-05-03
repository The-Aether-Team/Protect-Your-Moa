package com.aetherteam.protect_your_moa.capability.armor;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.protect_your_moa.capability.ProtectCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public interface MoaArmor extends INBTSynchable<CompoundTag> {
    Moa getMoa();

    static LazyOptional<MoaArmor> get(Moa moa) {
        return moa.getCapability(ProtectCapabilities.MOA_ARMOR_CAPABILITY);
    }

    void onJoinLevel();
    void onUpdate();

    void createInventory();
    void openInventory(ServerPlayer player, Moa moa);

    void equipSaddle();
    boolean isSaddled();

    void equipArmor(ItemStack armor);
    void setArmor(ItemStack itemStack);
    ItemStack getArmor();
    boolean isWearingArmor();

    void setChest(boolean chested);
    boolean hasChest();

    void setShouldSyncChest(boolean sync);
    boolean shouldSyncChest();

    SimpleContainer getInventory();

    boolean isArmor(ItemStack stack);
}
