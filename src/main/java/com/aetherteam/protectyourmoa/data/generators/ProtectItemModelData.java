package com.aetherteam.protectyourmoa.data.generators;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.item.ProtectItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ProtectItemModelData extends NitrogenItemModelProvider {
    public ProtectItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, ProtectYourMoa.MODID, helper);
    }

    @Override
    protected void registerModels() {
        this.item(ProtectItems.ZANITE_MOA_ARMOR.get(), "armor/");
        this.item(ProtectItems.GRAVITITE_MOA_ARMOR.get(), "armor/");
    }
}
