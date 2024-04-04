package com.aetherteam.protectyourmoa.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public abstract class ProtectRecipeProvider extends NitrogenRecipeProvider {
    public ProtectRecipeProvider(PackOutput output) {
        super(output, ProtectYourMoa.MODID);
    }

    public void moaArmor(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("  #")
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(result), has(result))
                .save(finishedRecipeConsumer);
    }

    public void moaArmor(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, TagKey<Item> material, String unlockName) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("#  ")
                .pattern("###")
                .pattern("# #")
                .unlockedBy("has_" + unlockName, has(result))
                .save(finishedRecipeConsumer);
    }
}
