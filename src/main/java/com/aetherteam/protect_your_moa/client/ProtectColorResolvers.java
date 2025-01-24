package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.component.DyedItemColor;

public class ProtectColorResolvers {
    public static void registerItemColor() {
        ColorProviderRegistry.ITEM.register((itemProvider, color) -> color > 0 ? -1 : DyedItemColor.getOrDefault(itemProvider, -6265536), ProtectItems.LEATHER_MOA_ARMOR.get());
    }
}
