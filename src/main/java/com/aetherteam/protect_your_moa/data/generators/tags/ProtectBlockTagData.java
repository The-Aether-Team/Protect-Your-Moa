package com.aetherteam.protect_your_moa.data.generators.tags;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ProtectBlockTagData extends BlockTagsProvider {
    public ProtectBlockTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, ProtectYourMoa.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
