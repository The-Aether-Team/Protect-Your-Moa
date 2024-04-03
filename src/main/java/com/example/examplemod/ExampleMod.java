package com.example.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Map;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus bus, Dist dist) {
        bus.addListener(this::dataSetup);
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.examplemod.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }
}
