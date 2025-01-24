//package com.aetherteam.protect_your_moa.data.providers;
//
//import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import net.minecraft.data.PackOutput;
//import net.minecraft.world.item.Item;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//
//public abstract class ProtectItemModelProvider extends NitrogenItemModelProvider {
//    public ProtectItemModelProvider(PackOutput output, ExistingFileHelper helper) {
//        super(output, ProtectYourMoa.MODID, helper);
//    }
//
//    public void dyedMoaArmorItem(Item item, String location) {
//        this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                .texture("layer0", this.modLoc("item/" + location + this.itemName(item)))
//                .texture("layer1", this.modLoc("item/" + location + this.itemName(item) + "_overlay"));
//    }
//}
