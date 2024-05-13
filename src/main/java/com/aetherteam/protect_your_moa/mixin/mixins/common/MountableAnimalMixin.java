package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.entity.passive.MountableAnimal;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MountableAnimal.class)
public class MountableAnimalMixin {
    @Inject(at = @At(value = "HEAD"), method = "equipSaddle(Lnet/minecraft/sounds/SoundSource;)V")
    private void equipSaddle(SoundSource soundCategory, CallbackInfo ci) {
        MountableAnimal mountableAnimal = (MountableAnimal) (Object) this;
        if (mountableAnimal instanceof Moa moa) {
            moa.getData(ProtectDataAttachments.MOA_ARMOR).equipSaddle(); //todo test
        }
    }
}
