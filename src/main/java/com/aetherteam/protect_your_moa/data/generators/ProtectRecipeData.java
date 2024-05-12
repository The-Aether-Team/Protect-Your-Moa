package com.aetherteam.protect_your_moa.data.generators;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.protect_your_moa.data.providers.ProtectRecipeProvider;
import com.aetherteam.protect_your_moa.item.ProtectItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.Tags;

public class ProtectRecipeData extends ProtectRecipeProvider {
    public ProtectRecipeData(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        this.moaArmor(recipeOutput, ProtectItems.LEATHER_MOA_ARMOR.get(), Tags.Items.LEATHER, "leather");
        this.moaArmor(recipeOutput, ProtectItems.IRON_MOA_ARMOR.get(), Tags.Items.INGOTS_IRON, "iron");
        this.moaArmor(recipeOutput, ProtectItems.GOLDEN_MOA_ARMOR.get(), Tags.Items.INGOTS_GOLD, "gold");
        this.moaArmor(recipeOutput, ProtectItems.DIAMOND_MOA_ARMOR.get(), Tags.Items.GEMS_DIAMOND, "diamond");
        this.moaArmor(recipeOutput, ProtectItems.ZANITE_MOA_ARMOR.get(), AetherTags.Items.GEMS_ZANITE, "zanite");
        this.moaArmor(recipeOutput, ProtectItems.GRAVITITE_MOA_ARMOR.get(), AetherTags.Items.PROCESSED_GRAVITITE, "gravitite");
    }
}
