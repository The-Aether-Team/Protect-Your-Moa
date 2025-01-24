package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 1100)
public class EntityMixin {

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER),
            method = "load"
    )
    private void protectMoa$readEntityAttachments(CompoundTag nbt, CallbackInfo cir) {
        if (((Entity)(Object) this) instanceof Moa moa) {
            var data = moa.getAttached(ProtectDataAttachments.MOA_ARMOR);

            if (data != null) data.setMoa(moa);
        }
    }
}
