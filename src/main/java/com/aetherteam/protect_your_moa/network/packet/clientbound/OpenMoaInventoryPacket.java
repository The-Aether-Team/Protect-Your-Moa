package com.aetherteam.protect_your_moa.network.packet.clientbound;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.client.MoaArmorClient;
import io.wispforest.accessories.networking.BaseAccessoriesPacket;
import io.wispforest.accessories.networking.base.BaseNetworkHandler;
import io.wispforest.accessories.networking.base.HandledPacketPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenMoaInventoryPacket(int entityId, int containerSize, byte containerId) implements BaseAccessoriesPacket, CustomPacketPayload {
    public static final Type<OpenMoaInventoryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "open_moa_inventory"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenMoaInventoryPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            OpenMoaInventoryPacket::entityId,
            ByteBufCodecs.VAR_INT,
            OpenMoaInventoryPacket::containerSize,
            ByteBufCodecs.BYTE,
            OpenMoaInventoryPacket::containerId,
            OpenMoaInventoryPacket::new);

    public static void execute(OpenMoaInventoryPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(payload.entityId());
            if (entity instanceof Moa moa) {
                MoaArmorClient.setToMoaInventoryScreen(Minecraft.getInstance().player, moa, payload.containerSize(), payload.containerId());
            }
        }
    }

    @Override
    public BaseNetworkHandler handler() {
        return BaseAccessoriesPacket.super.handler();
    }

    @Override
    public Type<? extends HandledPacketPayload> type() {
        return TYPE;
    }
}
