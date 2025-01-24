//package com.aetherteam.protect_your_moa.data.providers;
//
//import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.recipes.RecipeCategory;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.minecraft.data.recipes.ShapedRecipeBuilder;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.ItemLike;
//import net.neoforged.neoforge.common.Tags;
//
//import java.util.concurrent.CompletableFuture;
//
//public abstract class ProtectRecipeProvider extends NitrogenRecipeProvider {
//    public ProtectRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
//        super(output, registries, ProtectYourMoa.MODID);
//    }
//
//    public void moaArmor(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
//                .define('#', material)
//                .define('L', Tags.Items.LEATHERS)
//                .pattern("  #")
//                .pattern("#L#")
//                .pattern("# #")
//                .unlockedBy(getHasName(result), has(result))
//                .save(recipeOutput);
//    }
//
//    public void moaArmor(RecipeOutput recipeOutput, ItemLike result, TagKey<Item> material, String unlockName) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
//                .define('#', material)
//                .define('L', Tags.Items.LEATHERS)
//                .pattern("  #")
//                .pattern("#L#")
//                .pattern("# #")
//                .unlockedBy("has_" + unlockName, has(result))
//                .save(recipeOutput);
//    }
//}
