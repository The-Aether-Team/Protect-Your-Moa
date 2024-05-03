package com.aetherteam.protect_your_moa.client.renderer.entity;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ProtectModelLayers {
    public static final ModelLayerLocation MOA_ARMOR = register("moa", "armor");
    public static final ModelLayerLocation MOA_CHEST = register("moa", "chest");

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(ProtectYourMoa.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
