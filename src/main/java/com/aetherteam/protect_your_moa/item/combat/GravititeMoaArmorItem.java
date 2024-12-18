package com.aetherteam.protect_your_moa.item.combat;

import com.aetherteam.aether.entity.ai.attribute.AetherAttributes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GravititeMoaArmorItem extends MoaArmorItem {
    public static final ResourceLocation TEXTURE_JUMPS = ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "hud/jumps_gravitite");
    public static final ResourceLocation BONUS_JUMPS_ID = ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "gravitite_moa_jumps");

    public GravititeMoaArmorItem() {
        super(11, "gravitite", new Item.Properties().stacksTo(1));
    }

    @Override
    public void tick(Moa moa, ItemStack itemStack) {
        AttributeInstance jumps = moa.getAttribute(AetherAttributes.MOA_MAX_JUMPS);
        if (jumps != null) {
            if (!jumps.hasModifier(this.getJumpsModifier().id())) {
                jumps.addTransientModifier(this.getJumpsModifier());
            }
        }
        super.tick(moa, itemStack);
    }

    @Override
    public void onUnequip(Moa moa, ItemStack itemStack) {
        AttributeInstance jumps = moa.getAttribute(AetherAttributes.MOA_MAX_JUMPS);
        if (jumps != null) {
            if (jumps.hasModifier(this.getJumpsModifier().id())) {
                jumps.removeModifier(this.getJumpsModifier().id());
            }
        }
        if (moa.getRemainingJumps() > moa.getMaxJumps()) {
            moa.setRemainingJumps(moa.getMaxJumps());
        }
        super.onUnequip(moa, itemStack);
    }

    public AttributeModifier getJumpsModifier() {
        return new AttributeModifier(BONUS_JUMPS_ID, 3, AttributeModifier.Operation.ADD_VALUE);
    }
}
