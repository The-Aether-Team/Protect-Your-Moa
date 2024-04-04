package com.aetherteam.protectyourmoa.client.renderer.entity;

import com.aetherteam.protectyourmoa.ProtectYourMoa;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ProtectModelLayers {
    public static final ModelLayerLocation MOA_ARMOR = register("moa", "armor");

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(ProtectYourMoa.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
