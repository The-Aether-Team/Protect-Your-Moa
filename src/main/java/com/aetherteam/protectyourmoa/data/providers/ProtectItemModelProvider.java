package com.aetherteam.protectyourmoa.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ProtectItemModelProvider extends NitrogenItemModelProvider {
    public ProtectItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ProtectYourMoa.MODID, helper);
    }

    public void dyedMoaArmorItem(Item item, String location) {
        ItemModelBuilder builder = this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_overlay"));
    }
}
