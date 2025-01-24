//package com.aetherteam.protect_your_moa.data.generators;
//
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.protect_your_moa.data.providers.ProtectRecipeProvider;
//import com.aetherteam.protect_your_moa.item.ProtectItems;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.neoforged.neoforge.common.Tags;
//
//import java.util.concurrent.CompletableFuture;
//
//public class ProtectRecipeData extends ProtectRecipeProvider {
//    public ProtectRecipeData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
//        super(output, registries);
//    }
//
//    @Override
//    protected void buildRecipes(RecipeOutput recipeOutput) {
//        this.moaArmor(recipeOutput, ProtectItems.LEATHER_MOA_ARMOR.get(), Tags.Items.LEATHERS, "leather");
//        this.moaArmor(recipeOutput, ProtectItems.IRON_MOA_ARMOR.get(), Tags.Items.INGOTS_IRON, "iron");
//        this.moaArmor(recipeOutput, ProtectItems.GOLDEN_MOA_ARMOR.get(), Tags.Items.INGOTS_GOLD, "gold");
//        this.moaArmor(recipeOutput, ProtectItems.DIAMOND_MOA_ARMOR.get(), Tags.Items.GEMS_DIAMOND, "diamond");
//        this.moaArmor(recipeOutput, ProtectItems.ZANITE_MOA_ARMOR.get(), AetherTags.Items.GEMS_ZANITE, "zanite");
//        this.moaArmor(recipeOutput, ProtectItems.GRAVITITE_MOA_ARMOR.get(), AetherTags.Items.PROCESSED_GRAVITITE, "gravitite");
//    }
//}
