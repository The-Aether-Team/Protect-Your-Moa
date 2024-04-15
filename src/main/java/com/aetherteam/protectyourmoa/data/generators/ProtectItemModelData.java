package com.aetherteam.protectyourmoa.data.generators;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.data.providers.ProtectItemModelProvider;
import com.aetherteam.protectyourmoa.item.ProtectItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ProtectItemModelData extends ProtectItemModelProvider {
    public ProtectItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    protected void registerModels() {
        this.dyedMoaArmorItem(ProtectItems.LEATHER_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.IRON_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.GOLDEN_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.DIAMOND_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.ZANITE_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.GRAVITITE_MOA_ARMOR.get(), "armor/");
    }
}
