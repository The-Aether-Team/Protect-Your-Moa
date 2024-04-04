package com.aetherteam.protectyourmoa.mixin.mixins.client;

import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Inject(at = @At(value = "HEAD"), method = "isServerControlledInventory()Z", cancellable = true)
    private void isServerControlledInventory(CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.isPassenger() && player.getVehicle() instanceof Moa) {
            cir.setReturnValue(true);
        }
    }
}
