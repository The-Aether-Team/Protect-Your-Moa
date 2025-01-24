package com.aetherteam.protect_your_moa.event.listeners;

import com.aetherteam.aetherfabric.events.EntityEvents;
import com.aetherteam.aetherfabric.events.EntityTickEvents;
import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import com.aetherteam.aetherfabric.events.PlayerEvents;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.event.hooks.EntityHooks;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.impl.event.interaction.InteractionEventsRouter;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Optional;

public class EntityListener {

    public static void listen() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            return onInteractWithEntity(player, entity, player.getItemInHand(hand))
                    .orElse(InteractionResult.PASS);
        });

        EntityTickEvents.AFTER.register(EntityListener::onEntityTick);
    }

    public static Optional<InteractionResult> onInteractWithEntity(Player player, Entity targetEntity, ItemStack itemStack) {
        Optional<InteractionResult> result = EntityHooks.interactionOpenMoaInventory(targetEntity, player);
        if (result.isEmpty()) {
            result = EntityHooks.equipMoaArmor(targetEntity, player, itemStack);
        }
        if (result.isEmpty()) {
            result = EntityHooks.equipMoaChest(targetEntity, player, itemStack);
        }
        return result;
    }

    public static void onEntityJoin(Entity entity) {
        EntityHooks.onJoinLevel(entity);
    }

    public static void onEntityTick(Entity entity) {
        EntityHooks.onUpdate(entity);
    }

    public static void onEntityDrops(Entity entity, Collection<ItemEntity> drops) {
        EntityHooks.onDrops(entity, drops);
    }
}
