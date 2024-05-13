package com.aetherteam.protect_your_moa.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.client.renderer.entity.ProtectModelLayers;
import com.aetherteam.protect_your_moa.item.combat.DyeableMoaArmorItem;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.HorseArmorLayer}.
 */
public class MoaArmorLayer extends RenderLayer<Moa, MoaModel> {
    private final MoaModel model;

    public MoaArmorLayer(RenderLayerParent<Moa, MoaModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new MoaModel(modelSet.bakeLayer(ProtectModelLayers.MOA_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = moa.getData(ProtectDataAttachments.MOA_ARMOR).getArmor();
        if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof MoaArmorItem moaArmorItem) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            float f = 1.0F;
            float f1 = 1.0F;
            float f2 = 1.0F;
            if (itemStack.getItem() instanceof DyeableMoaArmorItem dyeableMoaArmorItem) {
                int i = dyeableMoaArmorItem.getColor(itemStack);
                f = (float)(i >> 16 & 255) / 255.0F;
                f1 = (float)(i >> 8 & 255) / 255.0F;
                f2 = (float)(i & 255) / 255.0F;
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(dyeableMoaArmorItem.getOverlayTexture()));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(moaArmorItem.getTexture()));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
        }
    }
}

