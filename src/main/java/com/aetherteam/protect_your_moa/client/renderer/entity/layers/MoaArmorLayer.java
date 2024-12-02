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
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

/**
 * [CODE COPY] - {@link HorseArmorLayer}.
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
            int i = -1;
            if (moaArmorItem instanceof DyeableMoaArmorItem dyeableArmor) {
                i = FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, -6265536));
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(dyeableArmor.getOverlayTexture()));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            }
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(moaArmorItem.getTexture()));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, i);
        }
    }
}

