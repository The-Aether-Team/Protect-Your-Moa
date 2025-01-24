//package com.aetherteam.protect_your_moa.data.generators;
//
//import com.aetherteam.aether.data.providers.AetherLanguageProvider;
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import com.aetherteam.protect_your_moa.item.ProtectItems;
//import net.minecraft.data.PackOutput;
//
//public class ProtectLanguageData extends AetherLanguageProvider {
//    public ProtectLanguageData(PackOutput output) {
//        super(output, ProtectYourMoa.MODID);
//    }
//
//    @Override
//    protected void addTranslations() {
//        this.addItem(ProtectItems.LEATHER_MOA_ARMOR, "Leather Moa Armor");
//        this.addItem(ProtectItems.IRON_MOA_ARMOR, "Iron Moa Armor");
//        this.addItem(ProtectItems.GOLDEN_MOA_ARMOR, "Golden Moa Armor");
//        this.addItem(ProtectItems.DIAMOND_MOA_ARMOR, "Diamond Moa Armor");
//        this.addItem(ProtectItems.ZANITE_MOA_ARMOR, "Zanite Moa Armor");
//        this.addItem(ProtectItems.GRAVITITE_MOA_ARMOR, "Gravitite Moa Armor");
//
//        this.addSubtitle("entity", "moa.chest", "Moa Chest equips");
//
//        this.addLore(ProtectItems.LEATHER_MOA_ARMOR, "A dyeable piece of leather armor for your Moa to wear, offering a small amount of protection.");
//        this.addLore(ProtectItems.IRON_MOA_ARMOR, "A piece of iron armor for your Moa to wear, offering some amount of protection.");
//        this.addLore(ProtectItems.GOLDEN_MOA_ARMOR, "A piece of golden armor for your Moa to wear, offering a decent amount of protection.");
//        this.addLore(ProtectItems.DIAMOND_MOA_ARMOR, "A piece of iron armor for your Moa to wear, offering a strong amount of protection.");
//        this.addLore(ProtectItems.ZANITE_MOA_ARMOR, "A piece of zanite armor for your Moa to wear, offering some amount of protection.");
//        this.addLore(ProtectItems.GRAVITITE_MOA_ARMOR, "A piece of gravitite armor for your Moa to wear, offering a strong amount of protection and increasing the amount of times it can jump.");
//
//        this.addPackDescription("mod", "The Aether: Protect Your Moa Resources");
//    }
//}
