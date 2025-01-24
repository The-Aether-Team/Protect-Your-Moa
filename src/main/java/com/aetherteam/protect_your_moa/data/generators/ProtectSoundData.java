//package com.aetherteam.protect_your_moa.data.generators;
//
//import com.aetherteam.protect_your_moa.ProtectYourMoa;
//import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
//import net.minecraft.data.PackOutput;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
//
//public class ProtectSoundData extends SoundDefinitionsProvider {
//    public ProtectSoundData(PackOutput output, ExistingFileHelper helper) {
//        super(output, ProtectYourMoa.MODID, helper);
//    }
//
//    @Override
//    public void registerSounds() {
//        this.add(ProtectSoundEvents.ENTITY_MOA_CHEST,
//                definition().with(sound("minecraft:mob/chicken/plop"))
//                        .subtitle("subtitles.aether_protect_your_moa.entity.moa.chest")
//        );
//    }
//}
