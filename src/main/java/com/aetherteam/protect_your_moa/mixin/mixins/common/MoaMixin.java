package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Moa.class)
public class MoaMixin {
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "getMaxJumps()I", remap = false)
    private int getMaxJumps(int original) {
        Moa moa = (Moa) (Object) this;
        LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
        if (moaArmorLazyOptional.isPresent()) {
            Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
            if (moaArmorOptional.isPresent()) {
                MoaArmor moaArmor = moaArmorOptional.get();
                if (moaArmor.getArmor() != null && moaArmor.getArmor().is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
                    return original + 3;
                }
            }
        }
        return original;
    }
}
