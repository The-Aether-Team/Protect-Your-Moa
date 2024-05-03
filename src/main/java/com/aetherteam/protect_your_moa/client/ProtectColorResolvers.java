package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectColorResolvers {
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), ProtectItems.LEATHER_MOA_ARMOR.get());
    }
}
