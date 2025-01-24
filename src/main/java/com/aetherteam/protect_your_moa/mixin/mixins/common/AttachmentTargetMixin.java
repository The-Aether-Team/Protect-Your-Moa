package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.attachment.MoaArmorAttachment;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AttachmentTarget.class, remap = false)
public interface AttachmentTargetMixin {

    @WrapOperation(method = "getAttachedOrCreate(Lnet/fabricmc/fabric/api/attachment/v1/AttachmentType;Ljava/util/function/Supplier;)Ljava/lang/Object;",
            at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/attachment/v1/AttachmentTarget;setAttached(Lnet/fabricmc/fabric/api/attachment/v1/AttachmentType;Ljava/lang/Object;)Ljava/lang/Object;"),
            remap = false)
    private <A> @Nullable A protectMoa$setMoaEntity(AttachmentTarget instance, AttachmentType<A> type, @Nullable A value, Operation<A> original) {
        if (type.equals(ProtectDataAttachments.MOA_ARMOR) && ((AttachmentTarget) this) instanceof Moa moa && value instanceof MoaArmorAttachment attachment) {
            attachment.setMoa(moa);
        }

        return original.call(instance, type, value);
    }
}
