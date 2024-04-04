package com.aetherteam.protectyourmoa.item.combat;

import com.aetherteam.protectyourmoa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MoaArmorItem extends Item {
    private final int protection;
    private final ResourceLocation texture;

    public MoaArmorItem(int protection, String identifier, Item.Properties properties) {
        this(protection, new ResourceLocation(ProtectYourMoa.MODID, "textures/entity/moa/armor/moa_armor_" + identifier + ".png"), properties);
    }

    public MoaArmorItem(int protection, ResourceLocation identifier, Item.Properties properties) {
        super(properties);
        this.protection = protection;
        this.texture = identifier;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public int getProtection() {
        return this.protection;
    }
}
