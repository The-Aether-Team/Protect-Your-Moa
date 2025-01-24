package com.aetherteam.protect_your_moa.network.packet;

import com.aetherteam.aetherfabric.network.handling.IPayloadContext;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.attachment.MoaArmorAttachment;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class MoaArmorSyncPacket extends SyncEntityPacket<MoaArmorAttachment> {
    public static final Type<MoaArmorSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "sync_moa_armor_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MoaArmorSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(MoaArmorSyncPacket::write, MoaArmorSyncPacket::decode);

    public MoaArmorSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public MoaArmorSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    @Override
    public Type<MoaArmorSyncPacket> type() {
        return TYPE;
    }

    public static MoaArmorSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new MoaArmorSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<MoaArmorAttachment>> getAttachment() {
        return () -> ProtectDataAttachments.MOA_ARMOR;
    }

    public static void execute(MoaArmorSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
