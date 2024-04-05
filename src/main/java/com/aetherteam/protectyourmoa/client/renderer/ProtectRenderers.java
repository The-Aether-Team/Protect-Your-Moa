package com.aetherteam.protectyourmoa.client.renderer;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.client.renderer.entity.ProtectModelLayers;
import com.aetherteam.protectyourmoa.client.renderer.entity.layers.MoaArmorLayer;
import com.aetherteam.protectyourmoa.client.renderer.entity.layers.MoaChestLayer;
import com.aetherteam.protectyourmoa.client.renderer.entity.model.MoaChestModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectRenderers {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ProtectModelLayers.MOA_ARMOR, () -> MoaModel.createBodyLayer(new CubeDeformation(0.15F)));
        event.registerLayerDefinition(ProtectModelLayers.MOA_CHEST, MoaChestModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
        LivingEntityRenderer<Moa, MoaModel> renderer = event.getRenderer(AetherEntityTypes.MOA.get());
        if (renderer != null) {
            renderer.addLayer(new MoaArmorLayer(renderer, Minecraft.getInstance().getEntityModels()));
            renderer.addLayer(new MoaChestLayer(renderer, Minecraft.getInstance().getEntityModels()));
        }
    }
}
