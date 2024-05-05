package com.aetherteam.protect_your_moa.item.combat;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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

    public void onEquip(Moa moa, ItemStack itemStack) {

    }

    public void onUnequip(Moa moa, ItemStack itemStack) {

    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public int getProtection() {
        return this.protection;
    }
}
