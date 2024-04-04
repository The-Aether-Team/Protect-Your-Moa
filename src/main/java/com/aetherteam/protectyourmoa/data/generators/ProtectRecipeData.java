package com.aetherteam.protectyourmoa.data.generators;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.protectyourmoa.data.providers.ProtectRecipeProvider;
import com.aetherteam.protectyourmoa.item.ProtectItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class ProtectRecipeData extends ProtectRecipeProvider {
    public ProtectRecipeData(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        this.moaArmor(consumer, ProtectItems.ZANITE_MOA_ARMOR.get(), AetherTags.Items.GEMS_ZANITE, "zanite");
        this.moaArmor(consumer, ProtectItems.GRAVITITE_MOA_ARMOR.get(), AetherTags.Items.PROCESSED_GRAVITITE, "gravitite");
    }
}
