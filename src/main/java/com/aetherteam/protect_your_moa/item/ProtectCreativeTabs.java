package com.aetherteam.protect_your_moa.item;

import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ProtectCreativeTabs {
    public static void buildCreativeModeTabs(CreativeModeTab tab, FabricItemGroupEntries entries) {
        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.get()) {
            entries.addAfter(new ItemStack(AetherItems.SENTRY_BOOTS.get()), new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()));
            entries.addAfter(new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()), new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()));
            entries.addAfter(new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()), new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()));
            entries.addAfter(new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()), new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()));
            entries.addAfter(new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()), new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()));
            entries.addAfter(new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()), new ItemStack(ProtectItems.GRAVITITE_MOA_ARMOR.get()));
        }
    }
}
