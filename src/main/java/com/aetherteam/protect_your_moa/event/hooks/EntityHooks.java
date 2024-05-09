package com.aetherteam.protect_your_moa.event.hooks;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Collection;
import java.util.Optional;

public class EntityHooks {
    public static Optional<InteractionResult> interactionOpenMoaInventory(Entity target, Player player) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
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
            return Optional.of(InteractionResult.sidedSuccess(player.getLevel().isClientSide()));
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> equipMoaArmor(Entity target, Player player, ItemStack stack) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && !moa.isVehicle()) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (!stack.isEmpty() && stack.getItem() instanceof MoaArmorItem && moaArmor.isArmor(stack) && !player.isSecondaryUseActive() && !moaArmor.isWearingArmor()) {
                        if (!player.getLevel().isClientSide()) {
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
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && !moa.isVehicle()) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    if (!stack.isEmpty() && stack.is(Items.CHEST) && !player.isSecondaryUseActive() && !moaArmor.hasChest()) {
                        if (!player.getLevel().isClientSide()) {
                            moaArmor.setSynched(INBTSynchable.Direction.CLIENT, "setChest", true);
                            moa.playSound(ProtectSoundEvents.ENTITY_MOA_CHEST.get(), 1.0F, (moa.getRandom().nextFloat() - moa.getRandom().nextFloat()) * 0.2F + 1.0F);
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

    public static void onDrops(Entity entity, Collection<ItemEntity> drops) {
        if (entity instanceof Moa moa) {
            LazyOptional<MoaArmor> moaArmorLazyOptional = MoaArmor.get(moa);
            if (moaArmorLazyOptional.isPresent()) {
                Optional<MoaArmor> moaArmorOptional = moaArmorLazyOptional.resolve();
                if (moaArmorOptional.isPresent()) {
                    MoaArmor moaArmor = moaArmorOptional.get();
                    Container inventory = moaArmor.getInventory();
                    if (moaArmor.hasChest()) {
                        if (!moa.getLevel().isClientSide()) {
                            ItemEntity itemEntity = new ItemEntity(moa.getLevel(), moa.getX(), moa.getY() + (double) 0.0F, moa.getZ(), new ItemStack(Blocks.CHEST));
                            itemEntity.setDefaultPickUpDelay();
                            drops.add(itemEntity);
                        }
                        moaArmor.setChest(false);
                    }
                    if (inventory != null) {
                        drops.removeIf(itemEntity -> itemEntity.getItem().is(Items.SADDLE));
                        for (int i = 0; i < inventory.getContainerSize(); ++i) {
                            ItemStack itemStack = inventory.getItem(i);
                            if (!itemStack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemStack)) {
                                ItemEntity itemEntity = new ItemEntity(moa.getLevel(), moa.getX(), moa.getY() + (double) 0.0F, moa.getZ(), itemStack);
                                itemEntity.setDefaultPickUpDelay();
                                drops.add(itemEntity);
                            }
                        }
                    }
                }
            }
        }
    }
}
