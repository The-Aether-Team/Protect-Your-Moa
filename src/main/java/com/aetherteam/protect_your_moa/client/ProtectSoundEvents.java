package com.aetherteam.protect_your_moa.client;

import com.aetherteam.aetherfabric.registries.DeferredHolder;
import com.aetherteam.aetherfabric.registries.DeferredRegister;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ProtectSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ProtectYourMoa.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_CHEST = register("entity.moa.chest");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, location)));
    }
}
