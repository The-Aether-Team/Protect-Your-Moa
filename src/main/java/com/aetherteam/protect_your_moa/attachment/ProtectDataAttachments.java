package com.aetherteam.protect_your_moa.attachment;

import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;

public class ProtectDataAttachments {
    public static final AttachmentType<MoaArmorAttachment> MOA_ARMOR = AttachmentRegistry.<MoaArmorAttachment>builder()
            .persistent(MoaArmorAttachment.CODEC)
            .initializer(MoaArmorAttachment::new)
            .buildAndRegister(ResourceLocation.fromNamespaceAndPath(ProtectYourMoa.MODID, "moa_armor"));

    public static void init() {}
}
