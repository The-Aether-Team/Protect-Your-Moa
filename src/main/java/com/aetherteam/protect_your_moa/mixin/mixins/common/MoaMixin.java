package com.aetherteam.protect_your_moa.mixin.mixins.common;

import com.aetherteam.aether.entity.WingedBird;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.entity.passive.MountableAnimal;
import com.aetherteam.protect_your_moa.event.listeners.EntityListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(value = Moa.class, remap = false)
public abstract class MoaMixin extends MountableAnimal implements WingedBird {
    protected MoaMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        if (this.level().isClientSide) return;

        var drops = new ArrayList<ItemEntity>();
        EntityListener.onEntityDrops(this, drops);

        drops.forEach(itemEntity -> {
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
        });
    }


}
