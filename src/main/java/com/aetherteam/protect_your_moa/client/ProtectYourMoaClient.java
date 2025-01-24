package com.aetherteam.protect_your_moa.client;

import com.aetherteam.protect_your_moa.client.renderer.ProtectRenderers;
import com.aetherteam.protect_your_moa.event.listeners.EntityListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;

public class ProtectYourMoaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProtectColorResolvers.registerItemColor();

        ProtectRenderers.registerLayerDefinitions();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, helper, ctx) -> ProtectRenderers.addEntityLayers(type, renderer, helper));

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> EntityListener.onEntityJoin(entity));
    }
}
