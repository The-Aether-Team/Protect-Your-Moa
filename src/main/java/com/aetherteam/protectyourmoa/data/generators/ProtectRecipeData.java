package com.aetherteam.protectyourmoa.data.generators;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.protectyourmoa.data.providers.ProtectRecipeProvider;
import com.aetherteam.protectyourmoa.item.ProtectItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ProtectRecipeData extends ProtectRecipeProvider {
    public ProtectRecipeData(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        this.moaArmor(consumer, ProtectItems.LEATHER_MOA_ARMOR.get(), Tags.Items.LEATHER, "leather");
        this.moaArmor(consumer, ProtectItems.IRON_MOA_ARMOR.get(), Tags.Items.INGOTS_IRON, "iron");
        this.moaArmor(consumer, ProtectItems.GOLDEN_MOA_ARMOR.get(), Tags.Items.INGOTS_GOLD, "gold");
        this.moaArmor(consumer, ProtectItems.DIAMOND_MOA_ARMOR.get(), Tags.Items.GEMS_DIAMOND, "diamond");
        this.moaArmor(consumer, ProtectItems.ZANITE_MOA_ARMOR.get(), AetherTags.Items.GEMS_ZANITE, "zanite");
        this.moaArmor(consumer, ProtectItems.GRAVITITE_MOA_ARMOR.get(), AetherTags.Items.PROCESSED_GRAVITITE, "gravitite");
    }
}
