package com.aetherteam.protect_your_moa.item;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import com.aetherteam.protect_your_moa.item.combat.DyeableMoaArmorItem;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ProtectItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProtectYourMoa.MODID);

    public static final RegistryObject<Item> LEATHER_MOA_ARMOR = ITEMS.register("leather_moa_armor", () -> new DyeableMoaArmorItem(3, "leather", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_MOA_ARMOR = ITEMS.register("iron_moa_armor", () -> new MoaArmorItem(5, "iron", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_MOA_ARMOR = ITEMS.register("golden_moa_armor", () -> new MoaArmorItem(7, "gold", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_MOA_ARMOR = ITEMS.register("diamond_moa_armor", () -> new MoaArmorItem(11, "diamond", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ZANITE_MOA_ARMOR = ITEMS.register("zanite_moa_armor", () -> new MoaArmorItem(5, "zanite", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GRAVITITE_MOA_ARMOR = ITEMS.register("gravitite_moa_armor", () -> new MoaArmorItem(11, "gravitite", new Item.Properties().stacksTo(1)));
}
