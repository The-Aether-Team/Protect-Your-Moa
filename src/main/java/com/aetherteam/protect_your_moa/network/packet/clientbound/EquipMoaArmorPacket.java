package com.aetherteam.protect_your_moa.network.packet.clientbound;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record EquipMoaArmorPacket(int entityId, ItemStack armor) implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(ProtectYourMoa.MODID, "equip_moa_armor");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId());
        buf.writeItem(this.armor());
    }

    public static EquipMoaArmorPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        ItemStack armor = buf.readItem();
        return new EquipMoaArmorPacket(entityId, armor);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(this.entityId());
            if (entity instanceof Moa moa) {
                moa.getData(ProtectDataAttachments.MOA_ARMOR).setArmor(moa, this.armor());
            }
        }
    }
}
