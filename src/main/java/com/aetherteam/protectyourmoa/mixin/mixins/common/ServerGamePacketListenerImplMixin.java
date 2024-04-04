package com.aetherteam.protectyourmoa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protectyourmoa.capability.armor.MoaArmor;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getVehicle()Lnet/minecraft/world/entity/Entity;", shift = At.Shift.AFTER), method = "handlePlayerCommand(Lnet/minecraft/network/protocol/game/ServerboundPlayerCommandPacket;)V")
    private void isServerControlledInventory(ServerboundPlayerCommandPacket packet, CallbackInfo ci) {
        ServerGamePacketListenerImpl impl = (ServerGamePacketListenerImpl) (Object) this;
        Entity entity = impl.getPlayer().getVehicle();
        if (entity instanceof Moa moa) {
            MoaArmor.get(moa).ifPresent((moaArmor) -> moaArmor.openInventory(impl.getPlayer(), moa));
        }
    }
}
