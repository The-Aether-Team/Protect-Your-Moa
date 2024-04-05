package com.aetherteam.protectyourmoa.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.capability.armor.MoaArmor;
import com.aetherteam.protectyourmoa.client.renderer.entity.ProtectModelLayers;
import com.aetherteam.protectyourmoa.client.renderer.entity.model.MoaChestModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MoaChestLayer extends RenderLayer<Moa, MoaModel> {
    private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(ProtectYourMoa.MODID, "textures/entity/moa/chest/moa_chest.png");

    private final MoaChestModel model;

    public MoaChestLayer(RenderLayerParent<Moa, MoaModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new MoaChestModel(modelSet.bakeLayer(ProtectModelLayers.MOA_CHEST));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        MoaArmor.get(moa).ifPresent(moaArmor -> {
            if (moaArmor.hasChest()) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTick);
                this.model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(CHEST_LOCATION));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        });
    }
}
