package com.aetherteam.protect_your_moa.mixin.mixins.client.accessor;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LayerDefinition.class)
public interface LayerDefinitionAccessor {
    @Accessor("mesh")
    MeshDefinition protect_your_moa$getMesh();
}
