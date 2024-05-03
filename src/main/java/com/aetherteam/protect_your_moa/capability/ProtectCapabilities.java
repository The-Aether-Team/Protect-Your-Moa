package com.aetherteam.protect_your_moa.capability;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.CapabilityProvider;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmorCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectCapabilities {
    public static final Capability<MoaArmor> MOA_ARMOR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(MoaArmor.class);
    }

    @Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Moa moa) {
                event.addCapability(new ResourceLocation(ProtectYourMoa.MODID, "moa_armor"), new CapabilityProvider(ProtectCapabilities.MOA_ARMOR_CAPABILITY, new MoaArmorCapability(moa)));
            }
        }
    }
}
