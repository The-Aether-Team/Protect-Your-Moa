package com.aetherteam.protect_your_moa.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AetherOverlays.class)
public class AetherOverlaysMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledWidth()I", shift = At.Shift.BEFORE), method = "renderMoaJumps(Lnet/minecraft/client/gui/GuiGraphics;Lcom/mojang/blaze3d/platform/Window;Lnet/minecraft/client/player/LocalPlayer;)V", cancellable = true)
    private static void renderMoaJumps(GuiGraphics guiGraphics, Window window, LocalPlayer player, CallbackInfo ci, @Local Moa moa, @Local(ordinal = 0) int jumpCount) {
        int jumps = moa.getMaxJumps() - 3;
        if (moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor(moa) != null && moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor(moa).is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
            if (jumpCount > jumps - 1) {
                ci.cancel();
            }
        }
    }
}
