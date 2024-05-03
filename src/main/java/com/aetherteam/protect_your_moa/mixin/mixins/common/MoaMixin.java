package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Moa.class)
public class MoaMixin {
    @Inject(at = @At(value = "HEAD"), method = "getMaxJumps()I", cancellable = true, remap = false)
    private void getMaxJumps(CallbackInfoReturnable<Integer> cir) {
        Moa moa = (Moa) (Object) this;
        MoaType moaType = moa.getMoaType();
        int jumps = moaType != null ? moaType.getMaxJumps() : AetherMoaTypes.BLUE.get().getMaxJumps();
        LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
        if (moaArmorLazyOptional.isPresent()) {
            Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
            if (moaArmorOptional.isPresent()) {
                MoaArmor moaArmor = moaArmorOptional.get();
                if (moaArmor.getArmor() != null && moaArmor.getArmor().is(ProtectItems.GRAVITITE_MOA_ARMOR.get())) {
                    cir.setReturnValue(jumps + 3);
                }
            }
        }
    }
}
