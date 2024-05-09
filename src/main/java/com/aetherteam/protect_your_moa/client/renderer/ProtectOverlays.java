package com.aetherteam.protect_your_moa.client.renderer;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectOverlays {
    private static final ResourceLocation TEXTURE_JUMPS = new ResourceLocation(ProtectYourMoa.MODID, "textures/gui/jumps_gravitite.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("moa_jumps_gravitite", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(poseStack, window, player);
            }
        });
    }

    /**
     * [CODE COPY] - {@link com.aetherteam.aether.client.renderer.AetherOverlays#renderMoaJumps(GuiGraphics, Window, LocalPlayer)}.
     */
    private static void renderMoaJumps(PoseStack poseStack, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa) {
            int jumps = moa.getMaxJumps() - 3;
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (moaArmor.getArmor() != null && moaArmor.getArmor().is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
                        for (int jumpCount = jumps; jumpCount < moa.getMaxJumps(); jumpCount++) {
                            int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                            int yPos = 18;
                            RenderSystem.setShader(GameRenderer::getPositionTexShader);
                            RenderSystem.setShaderTexture(0, TEXTURE_JUMPS);
                            if (jumpCount < moa.getRemainingJumps()) {
                                GuiComponent.blit(poseStack, xPos, yPos, 0, 0, 9, 11, 256, 256);
                            } else {
                                GuiComponent.blit(poseStack, xPos, yPos, 10, 0, 9, 11, 256, 256);
                            }
                        }
                    }
                }
            }
        }
    }
}
