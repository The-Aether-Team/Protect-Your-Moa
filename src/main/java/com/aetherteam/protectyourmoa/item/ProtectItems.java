package com.aetherteam.protectyourmoa.item;

import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.aetherteam.protectyourmoa.item.combat.MoaArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ProtectItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProtectYourMoa.MODID);

    public static final RegistryObject<Item> ZANITE_MOA_ARMOR = ITEMS.register("zanite_moa_armor", () -> new MoaArmorItem(5, "zanite", new Item.Properties()));
    public static final RegistryObject<Item> GRAVITITE_MOA_ARMOR = ITEMS.register("gravitite_moa_armor", () -> new MoaArmorItem(11, "gravitite", new Item.Properties()));
}
