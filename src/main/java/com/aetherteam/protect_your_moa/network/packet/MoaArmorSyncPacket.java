package com.aetherteam.protect_your_moa.network.packet;

import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.attachment.MoaArmorAttachment;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class MoaArmorSyncPacket extends SyncEntityPacket<MoaArmorAttachment> {
    public static final Type ID = ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "sync_moa_armor_attachment");

    public MoaArmorSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public MoaArmorSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static MoaArmorSyncPacket decode(FriendlyByteBuf buf) {
        return new MoaArmorSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<MoaArmorAttachment>> getAttachment() {
        return ProtectDataAttachments.MOA_ARMOR;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
