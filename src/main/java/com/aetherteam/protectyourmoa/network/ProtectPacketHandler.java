package com.aetherteam.protectyourmoa.network;

import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.network.packet.MoaArmorSyncPacket;
import com.aetherteam.protectyourmoa.network.packet.clientbound.OpenMoaInventoryPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class ProtectPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ProtectYourMoa.MODID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    private static int index;

    public static synchronized void register() {
        register(OpenMoaInventoryPacket.class, OpenMoaInventoryPacket::decode);

        register(MoaArmorSyncPacket.class, MoaArmorSyncPacket::decode);
    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
