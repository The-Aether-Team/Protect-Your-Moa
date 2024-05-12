package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Moa.class)
public class MoaMixin {
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "getMaxJumps()I", remap = false)
    private int getMaxJumps(int original) {
        Moa moa = (Moa) (Object) this;
        if (moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor(moa) != null && moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor(moa).is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
            return original + 3;
        }
        return original;
    }
}
