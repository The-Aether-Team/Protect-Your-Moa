package com.aetherteam.protect_your_moa.client.renderer;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.client.renderer.entity.ProtectModelLayers;
import com.aetherteam.protect_your_moa.client.renderer.entity.layers.MoaArmorLayer;
import com.aetherteam.protect_your_moa.client.renderer.entity.layers.MoaChestLayer;
import com.aetherteam.protect_your_moa.client.renderer.entity.model.MoaChestModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class ProtectRenderers {
    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(ProtectModelLayers.MOA_ARMOR, () -> MoaModel.createBodyLayer(new CubeDeformation(0.25F)));
        EntityModelLayerRegistry.registerModelLayer(ProtectModelLayers.MOA_CHEST, MoaChestModel::createBodyLayer);
    }

    public static void addEntityLayers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> renderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper) {
        if (renderer != null && entityType.equals(AetherEntityTypes.MOA.get())) {
            var castedRenderer = (LivingEntityRenderer<Moa, MoaModel>)renderer;

            registrationHelper.register(new MoaArmorLayer(castedRenderer, Minecraft.getInstance().getEntityModels()));
            registrationHelper.register(new MoaChestLayer(castedRenderer, Minecraft.getInstance().getEntityModels()));
        }
    }
}
