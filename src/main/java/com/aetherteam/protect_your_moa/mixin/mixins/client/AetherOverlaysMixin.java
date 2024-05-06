package com.aetherteam.protect_your_moa.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AetherOverlays.class)
public class AetherOverlaysMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledWidth()I", shift = At.Shift.BEFORE), method = "renderMoaJumps(Lnet/minecraft/client/gui/GuiGraphics;Lcom/mojang/blaze3d/platform/Window;Lnet/minecraft/client/player/LocalPlayer;)V", cancellable = true)
    private static void renderMoaJumps(GuiGraphics guiGraphics, Window window, LocalPlayer player, CallbackInfo ci, @Local Moa moa, @Local(ordinal = 0) int jumpCount) {
        int jumps = moa.getMaxJumps() - 3;
        LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
        if (moaArmorLazyOptional.isPresent()) {
            Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
            if (moaArmorOptional.isPresent()) {
                MoaArmor moaArmor = moaArmorOptional.get();
                if (moaArmor.getArmor() != null && moaArmor.getArmor().is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
                    if (jumpCount > jumps - 1) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
