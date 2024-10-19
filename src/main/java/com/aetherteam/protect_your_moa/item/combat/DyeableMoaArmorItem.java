package com.aetherteam.protect_your_moa.item.combat;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeColor;

public class DyeableMoaArmorItem extends MoaArmorItem  {
    private final ResourceLocation overlayTexture;

    public DyeableMoaArmorItem(int protection, String identifier, Properties properties) {
        super(protection, identifier, properties);
        this.overlayTexture = ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "textures/entity/moa/armor/moa_armor_" + identifier + "_overlay.png");
    }

    public ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }
}
