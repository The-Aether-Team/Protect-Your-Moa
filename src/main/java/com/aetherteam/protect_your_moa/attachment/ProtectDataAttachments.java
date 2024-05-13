package com.aetherteam.protect_your_moa.attachment;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.ProtectYourMoa;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ProtectDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ProtectYourMoa.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MoaArmorAttachment>> MOA_ARMOR = ATTACHMENTS.register("moa_armor", () -> AttachmentType.serializable((moa) -> new MoaArmorAttachment((Moa) moa)).build());
}
