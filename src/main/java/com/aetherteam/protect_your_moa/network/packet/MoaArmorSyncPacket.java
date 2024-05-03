package com.aetherteam.protect_your_moa.network.packet;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class MoaArmorSyncPacket extends SyncEntityPacket<MoaArmor> {
    public MoaArmorSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public MoaArmorSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static MoaArmorSyncPacket decode(FriendlyByteBuf buf) {
        return new MoaArmorSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<MoaArmor> getCapability(Entity entity) {
        return MoaArmor.get((Moa) entity);
    }
}
