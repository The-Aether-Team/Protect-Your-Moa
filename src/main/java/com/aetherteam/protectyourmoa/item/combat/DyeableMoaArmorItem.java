package com.aetherteam.protectyourmoa.item.combat;

import com.aetherteam.protectyourmoa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;

public class DyeableMoaArmorItem extends MoaArmorItem implements DyeableLeatherItem {
    private final ResourceLocation overlayTexture;

    public DyeableMoaArmorItem(int protection, String identifier, Properties properties) {
        super(protection, identifier, properties);
        this.overlayTexture = new ResourceLocation(ProtectYourMoa.MODID, "textures/entity/moa/armor/moa_armor_" + identifier + "_overlay.png");
    }

    public ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }
}
