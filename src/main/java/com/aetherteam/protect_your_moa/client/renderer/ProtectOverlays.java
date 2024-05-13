package com.aetherteam.protect_your_moa.client.renderer;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectOverlays {
    private static final ResourceLocation TEXTURE_JUMPS = new ResourceLocation(ProtectYourMoa.MODID, "textures/gui/jumps_gravitite.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("moa_jumps_gravitite", (gui, graphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(graphics, window, player);
            }
        });
    }

    /**
     * [CODE COPY] - {@link com.aetherteam.aether.client.renderer.AetherOverlays#renderMoaJumps(GuiGraphics, Window, LocalPlayer)}.
     */
    private static void renderMoaJumps(GuiGraphics guiGraphics, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa) {
            int jumps = moa.getMaxJumps() - 3;
            if (moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor() != null && moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor().is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
                for (int jumpCount = jumps; jumpCount < moa.getMaxJumps(); jumpCount++) {
                    int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                    int yPos = 18;
                    if (jumpCount < moa.getRemainingJumps()) {
                        guiGraphics.blit(TEXTURE_JUMPS, xPos, yPos, 0, 0, 9, 11, 256, 256);
                    } else {
                        guiGraphics.blit(TEXTURE_JUMPS, xPos, yPos, 10, 0, 9, 11, 256, 256);
                    }
                }
            }
        }
    }
}
