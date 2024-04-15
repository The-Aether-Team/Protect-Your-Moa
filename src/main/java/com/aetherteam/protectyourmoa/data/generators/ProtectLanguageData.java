package com.aetherteam.protectyourmoa.data.generators;

import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.item.ProtectItems;
import net.minecraft.data.PackOutput;

public class ProtectLanguageData extends NitrogenLanguageProvider {
    public ProtectLanguageData(PackOutput output) {
        super(output, ProtectYourMoa.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addItem(ProtectItems.LEATHER_MOA_ARMOR, "Leather Moa Armor");
        this.addItem(ProtectItems.IRON_MOA_ARMOR, "Iron Moa Armor");
        this.addItem(ProtectItems.GOLDEN_MOA_ARMOR, "Golden Moa Armor");
        this.addItem(ProtectItems.DIAMOND_MOA_ARMOR, "Diamond Moa Armor");
        this.addItem(ProtectItems.ZANITE_MOA_ARMOR, "Zanite Moa Armor");
        this.addItem(ProtectItems.GRAVITITE_MOA_ARMOR, "Gravitite Moa Armor");

        this.addPackDescription("mod", "The Aether: Protect Your Moa Resources");
    }
}
