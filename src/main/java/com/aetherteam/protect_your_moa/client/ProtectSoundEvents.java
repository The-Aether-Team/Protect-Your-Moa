package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ProtectSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ProtectYourMoa.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_CHEST = register("entity.moa.chest");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ProtectYourMoa.MODID, location)));
    }
}
