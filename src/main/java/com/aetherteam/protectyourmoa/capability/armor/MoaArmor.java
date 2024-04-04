package com.aetherteam.protectyourmoa.capability.armor;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.protectyourmoa.capability.ProtectCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public interface MoaArmor extends INBTSynchable<CompoundTag> {
    Moa getMoa();

    static LazyOptional<MoaArmor> get(Moa moa) {
        return moa.getCapability(ProtectCapabilities.MOA_ARMOR_CAPABILITY);
    }

    void openInventory(ServerPlayer player, Moa moa);

    void equipSaddle();
    boolean isSaddled();

    void equipArmor(ItemStack armor);
    void setArmor(ItemStack itemStack);
    ItemStack getArmor();
    boolean isWearingArmor();

    SimpleContainer getInventory();

    boolean isArmor(ItemStack stack);
}
