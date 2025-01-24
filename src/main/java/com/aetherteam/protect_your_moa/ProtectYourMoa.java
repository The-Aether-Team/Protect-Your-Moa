package com.aetherteam.protect_your_moa;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aetherfabric.NetworkRegisterHelper;
import com.aetherteam.aetherfabric.registries.DeferredRegister;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
import com.aetherteam.protect_your_moa.event.listeners.EntityListener;
import com.aetherteam.protect_your_moa.item.ProtectCreativeTabs;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import com.aetherteam.protect_your_moa.item.combat.GravititeMoaArmorItem;
import com.aetherteam.protect_your_moa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protect_your_moa.network.packet.clientbound.OpenMoaInventoryPacket;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import org.slf4j.Logger;

public class ProtectYourMoa implements ModInitializer {
    public static final String MODID = "aether_protect_your_moa";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        //bus.addListener(ProtectData::dataSetup);
        this.commonSetup();
        this.registerPackets();

        ProtectDataAttachments.init();

        DeferredRegister<?>[] registers = {
                ProtectItems.ITEMS,
                ProtectSoundEvents.SOUNDS,
        };

        for (DeferredRegister<?> register : registers) {
            register.addEntriesToRegistry();
        }

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(ProtectCreativeTabs::buildCreativeModeTabs);

        EntityListener.listen();
    }

    public void commonSetup() {
        Moa.registerJumpOverlayTextureOverride(GravititeMoaArmorItem.BONUS_JUMPS_ID, GravititeMoaArmorItem.TEXTURE_JUMPS);
    }

    public void registerPackets() {
        NetworkRegisterHelper registrar = NetworkRegisterHelper.INSTANCE;
        // CLIENTBOUND
        registrar.playToClient(OpenMoaInventoryPacket.TYPE, OpenMoaInventoryPacket.STREAM_CODEC, OpenMoaInventoryPacket::execute);

        // BOTH
        registrar.playBidirectional(MoaArmorSyncPacket.TYPE, MoaArmorSyncPacket.STREAM_CODEC, MoaArmorSyncPacket::execute);
    }
}
