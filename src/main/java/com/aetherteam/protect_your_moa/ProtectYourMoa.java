package com.aetherteam.protect_your_moa;

import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
import com.aetherteam.protect_your_moa.data.ProtectData;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.aetherteam.protect_your_moa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protect_your_moa.network.packet.clientbound.OpenMoaInventoryPacket;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ProtectYourMoa.MODID)
public class ProtectYourMoa {
    public static final String MODID = "aether_protect_your_moa";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProtectYourMoa(IEventBus bus, Dist dist) {
        bus.addListener(ProtectData::dataSetup);
        bus.addListener(this::registerPackets);

        DeferredRegister<?>[] registers = {
                ProtectItems.ITEMS,
                ProtectSoundEvents.SOUNDS,
                ProtectDataAttachments.ATTACHMENTS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }
    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

        // CLIENTBOUND
        registrar.playToClient(OpenMoaInventoryPacket.TYPE, OpenMoaInventoryPacket.STREAM_CODEC, OpenMoaInventoryPacket::execute);

        // BOTH
        registrar.playBidirectional(MoaArmorSyncPacket.TYPE, MoaArmorSyncPacket.STREAM_CODEC, MoaArmorSyncPacket::execute);
    }
}
