package com.aetherteam.protect_your_moa.network.packet.clientbound;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.client.MoaArmorClient;
import io.wispforest.accessories.networking.BaseAccessoriesPacket;
import io.wispforest.accessories.networking.base.BaseNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record OpenMoaInventoryPacket(int entityId, int containerSize, int containerId) implements BaseAccessoriesPacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "open_moa_inventory");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId());
        buf.writeVarInt(this.containerSize());
        buf.writeByte(this.containerId());
    }

    public static OpenMoaInventoryPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int containerSize = buf.readVarInt();
        int containerId = buf.readUnsignedByte();
        return new OpenMoaInventoryPacket(entityId, containerSize, containerId);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(this.entityId());
            if (entity instanceof Moa moa) {
                MoaArmorClient.setToMoaInventoryScreen(Minecraft.getInstance().player, moa, this.containerSize(), this.containerId());
            }
        }
    }

    @Override
    public BaseNetworkHandler handler() {
        return BaseAccessoriesPacket.super.handler();
    }
}
