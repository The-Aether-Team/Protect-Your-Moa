package com.aetherteam.protectyourmoa.event.hooks;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.protectyourmoa.capability.armor.MoaArmor;
import com.aetherteam.protectyourmoa.item.combat.MoaArmorItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public class EntityHooks {
    public static Optional<InteractionResult> interactionOpenMoaInventory(Entity target, Player player) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (player instanceof ServerPlayer serverPlayer) {
                        moaArmor.openInventory(serverPlayer, moa);
                    }
                }
            }
            return Optional.of(InteractionResult.sidedSuccess(player.level().isClientSide()));
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> equipMoaArmor(Entity target, Player player, ItemStack stack) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isVehicle()) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (!stack.isEmpty() && stack.getItem() instanceof MoaArmorItem && moaArmor.isArmor(stack) && !player.isSecondaryUseActive() && !moaArmor.isWearingArmor()) {
                        if (!player.level().isClientSide()) {
                            moaArmor.setSynched(INBTSynchable.Direction.CLIENT, "equipArmor", stack);
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            return Optional.of(InteractionResult.CONSUME);
                        } else {
                            return Optional.of(InteractionResult.SUCCESS);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> equipMoaChest(Entity target, Player player, ItemStack stack) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isVehicle()) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (!stack.isEmpty() && stack.is(Items.CHEST) && !player.isSecondaryUseActive() && !moaArmor.hasChest()) {
                        if (!player.level().isClientSide()) {
                            moaArmor.setSynched(INBTSynchable.Direction.CLIENT, "setChest", true);
                            moa.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (moa.getRandom().nextFloat() - moa.getRandom().nextFloat()) * 0.2F + 1.0F);
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            moaArmor.createInventory();
                            return Optional.of(InteractionResult.CONSUME);
                        } else {
                            return Optional.of(InteractionResult.SUCCESS);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static void onJoinLevel(Entity entity) {
        if (entity instanceof Moa moa) {
            MoaArmor.get(moa).ifPresent(MoaArmor::onJoinLevel);
        }
    }

    public static void onUpdate(Entity entity) {
        if (entity instanceof Moa moa) {
            MoaArmor.get(moa).ifPresent(MoaArmor::onUpdate);
        }
    }
}
