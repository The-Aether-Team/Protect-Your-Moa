package com.aetherteam.protectyourmoa.client.renderer.entity.model;

import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protectyourmoa.mixin.mixins.client.accessor.LayerDefinitionAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MoaChestModel extends BipedBirdModel<Moa> {
    private final ModelPart leftChest = this.body.getChild("left_chest");
    private final ModelPart rightChest = this.body.getChild("right_chest");

    public MoaChestModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = ((LayerDefinitionAccessor) MoaModel.createBodyLayer(CubeDeformation.NONE)).protect_your_moa$getMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.getChild("body");
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 0).addBox(-4.99F, 0.0F, -6.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE, 0.5F, 0.5F);
        partdefinition1.addOrReplaceChild("left_chest", cubelistbuilder, PartPose.offsetAndRotation(8.99F, 11.5F, 0.0F, 0.0F, ((float) Math.PI / 2F), 0.0F));
        partdefinition1.addOrReplaceChild("right_chest", cubelistbuilder, PartPose.offsetAndRotation(2.01F, 11.5F, 0.0F, 0.0F, ((float) Math.PI / 2F), 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.leftChest.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightChest.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
