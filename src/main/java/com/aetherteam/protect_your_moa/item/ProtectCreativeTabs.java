package com.aetherteam.protect_your_moa.item;

//import com.aetherteam.aether.item.AetherCreativeTabs;
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.event.CreativeModeTabEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class ProtectCreativeTabs {
//    @SubscribeEvent
//    public static void buildCreativeModeTabs(CreativeModeTabEvent.BuildContents event) {
//        CreativeModeTab tab = event.getTab();
//        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES) {
//            event.getEntries().put(new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.getEntries().putAfter(new ItemStack(ProtectItems.LEATHER_MOA_ARMOR.get()), new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.getEntries().putAfter(new ItemStack(ProtectItems.IRON_MOA_ARMOR.get()), new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.getEntries().putAfter(new ItemStack(ProtectItems.GOLDEN_MOA_ARMOR.get()), new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.getEntries().putAfter(new ItemStack(ProtectItems.DIAMOND_MOA_ARMOR.get()), new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.getEntries().putAfter(new ItemStack(ProtectItems.ZANITE_MOA_ARMOR.get()), new ItemStack(ProtectItems.GRAVITITE_MOA_ARMOR.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//        }
//    }
//}
