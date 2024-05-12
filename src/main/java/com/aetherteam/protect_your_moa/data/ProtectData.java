package com.aetherteam.protect_your_moa.data;

import com.aetherteam.protect_your_moa.data.generators.ProtectItemModelData;
import com.aetherteam.protect_your_moa.data.generators.ProtectLanguageData;
import com.aetherteam.protect_your_moa.data.generators.ProtectRecipeData;
import com.aetherteam.protect_your_moa.data.generators.ProtectSoundData;
import net.minecraft.DetectedVersion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Optional;

public class ProtectData {
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ProtectItemModelData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ProtectLanguageData(packOutput));
        generator.addProvider(event.includeClient(), new ProtectSoundData(packOutput, fileHelper));

        // Server Data
        generator.addProvider(event.includeServer(), new ProtectRecipeData(packOutput));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_protect_your_moa.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }
}
