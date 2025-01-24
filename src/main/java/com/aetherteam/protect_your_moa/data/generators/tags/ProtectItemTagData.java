//package com.aetherteam.protect_your_moa.data.generators.tags;
//
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import com.aetherteam.protect_your_moa.item.ProtectItems;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.tags.ItemTagsProvider;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.world.level.block.Block;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//
//import javax.annotation.Nullable;
//import java.util.concurrent.CompletableFuture;
//
//public class ProtectItemTagData extends ItemTagsProvider {
//    public ProtectItemTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
//        super(output, registries, blockTags, ProtectYourMoa.MODID, helper);
//    }
//
//    @Override
//    public void addTags(HolderLookup.Provider provider) {
//        this.tag(ItemTags.DYEABLE).add(ProtectItems.LEATHER_MOA_ARMOR.get());
//    }
//}
