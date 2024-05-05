package com.aetherteam.protect_your_moa;

import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
import com.aetherteam.protect_your_moa.data.ProtectData;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.aetherteam.protect_your_moa.network.ProtectPacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ProtectYourMoa.MODID)
public class ProtectYourMoa {
    public static final String MODID = "aether_protect_your_moa";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProtectYourMoa() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ProtectData::dataSetup);
        modEventBus.addListener(this::commonSetup);

        DeferredRegister<?>[] registers = {
                ProtectItems.ITEMS,
                ProtectSoundEvents.SOUNDS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        ProtectPacketHandler.register();
    }
}
