package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ProtectSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ProtectYourMoa.MODID);

    public static final RegistryObject<SoundEvent> ENTITY_MOA_CHEST = register("entity.moa.chest");

    private static RegistryObject<SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> new SoundEvent(new ResourceLocation(ProtectYourMoa.MODID, location)));
    }
}
