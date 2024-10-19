package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ProtectColorResolvers {
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider > 0 ? -1 : color.getItem().getBarColor(color), ProtectItems.LEATHER_MOA_ARMOR.get());
    }
}
