package com.aetherteam.protect_your_moa.item;

import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = ProtectYourMoa.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ProtectCreativeTabs {
    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();
        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.get()) {
            event.insertAfter(new ItemStack(AetherItems.SENTRY_BOOTS.get()), new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()), new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()), new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()), new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()), new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()), new ItemStack(ProtectItems.GRAVITITE_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
