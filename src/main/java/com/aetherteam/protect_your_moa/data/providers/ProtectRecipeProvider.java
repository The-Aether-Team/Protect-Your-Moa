package com.aetherteam.protect_your_moa.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public abstract class ProtectRecipeProvider extends NitrogenRecipeProvider {
    public ProtectRecipeProvider(PackOutput output) {
        super(output, ProtectYourMoa.MODID);
    }

    public void moaArmor(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .define('L', Tags.Items.LEATHER)
                .pattern("  #")
                .pattern("#L#")
                .pattern("# #")
                .unlockedBy(getHasName(result), has(result))
                .save(finishedRecipeConsumer);
    }

    public void moaArmor(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, TagKey<Item> material, String unlockName) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .define('L', Tags.Items.LEATHER)
                .pattern("  #")
                .pattern("#L#")
                .pattern("# #")
                .unlockedBy("has_" + unlockName, has(result))
                .save(finishedRecipeConsumer);
    }
}
