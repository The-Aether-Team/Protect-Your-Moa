package com.aetherteam.protect_your_moa.client;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.client.gui.screen.inventory.MoaInventoryScreen;
import com.aetherteam.protect_your_moa.inventory.menu.MoaInventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.SimpleContainer;

public class MoaArmorClient {
    public static void setToMoaInventoryScreen(LocalPlayer localPlayer, Moa moa, int containerSize, int containerId) {
        SimpleContainer simpleContainer = new SimpleContainer(containerSize);
        MoaInventoryMenu menu = new MoaInventoryMenu(containerId, localPlayer.getInventory(), simpleContainer, moa);
        localPlayer.containerMenu = menu;
        Minecraft.getInstance().setScreen(new MoaInventoryScreen(menu, localPlayer.getInventory(), moa));
    }
}
