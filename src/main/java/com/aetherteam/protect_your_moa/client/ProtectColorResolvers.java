package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ProtectColorResolvers {
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((itemStack, color) -> color > 0 ? -1 : DyedItemColor.getOrDefault(itemStack, -6265536), ProtectItems.LEATHER_MOA_ARMOR.get());
    }
}
