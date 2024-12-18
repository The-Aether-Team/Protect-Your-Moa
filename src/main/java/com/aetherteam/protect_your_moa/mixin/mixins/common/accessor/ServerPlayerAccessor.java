package com.aetherteam.protect_your_moa.mixin.mixins.common.accessor;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayer.class)
public interface ServerPlayerAccessor {
    @Accessor("containerCounter")
    int protect_your_moa$getContainerCounter();

    @Invoker
    void callNextContainerCounter();

    @Invoker
    void callInitMenu(AbstractContainerMenu menu);
}
