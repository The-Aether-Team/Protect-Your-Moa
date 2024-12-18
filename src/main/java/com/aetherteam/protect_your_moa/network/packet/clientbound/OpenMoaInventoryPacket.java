package com.aetherteam.protect_your_moa.network.packet.clientbound;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.client.MoaArmorClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenMoaInventoryPacket(int entityId, int containerSize, int containerId) implements CustomPacketPayload {
    public static final Type<OpenMoaInventoryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "open_moa_inventory"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenMoaInventoryPacket> STREAM_CODEC = CustomPacketPayload.codec(OpenMoaInventoryPacket::write, OpenMoaInventoryPacket::decode);

    @Override
    public Type<OpenMoaInventoryPacket> type() {
        return TYPE;
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityId());
        buf.writeVarInt(this.containerSize());
        buf.writeByte(this.containerId());
    }

    public static OpenMoaInventoryPacket decode(RegistryFriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int containerSize = buf.readVarInt();
        int containerId = buf.readUnsignedByte();
        return new OpenMoaInventoryPacket(entityId, containerSize, containerId);
    }

    public static void execute(OpenMoaInventoryPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(payload.entityId());
            if (entity instanceof Moa moa) {
                MoaArmorClient.setToMoaInventoryScreen(Minecraft.getInstance().player, moa, payload.containerSize(), payload.containerId());
            }
        }
    }
}
