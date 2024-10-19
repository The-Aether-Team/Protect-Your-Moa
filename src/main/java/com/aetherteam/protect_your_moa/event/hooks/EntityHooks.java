package com.aetherteam.protect_your_moa.event.hooks;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.protect_your_moa.attachment.ProtectDataAttachments;
import com.aetherteam.protect_your_moa.client.ProtectSoundEvents;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;

import java.util.Collection;
import java.util.Optional;

public class EntityHooks {
    public static Optional<InteractionResult> interactionOpenMoaInventory(Entity target, Player player) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            if (player instanceof ServerPlayer serverPlayer) {
                moa.getData(ProtectDataAttachments.MOA_ARMOR).openInventory(serverPlayer, moa);
            }
            return Optional.of(InteractionResult.sidedSuccess(player.level().isClientSide()));
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> equipMoaArmor(Entity target, Player player, ItemStack stack) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && !moa.isVehicle()) {
            if (!stack.isEmpty() && stack.getItem() instanceof MoaArmorItem && moa.getData(ProtectDataAttachments.MOA_ARMOR).isArmor(stack) && !player.isSecondaryUseActive() && !moa.getData(ProtectDataAttachments.MOA_ARMOR).isWearingArmor()) {
                if (!player.level().isClientSide()) {
                    moa.getData(ProtectDataAttachments.MOA_ARMOR).setSynched(moa.getId(), INBTSynchable.Direction.CLIENT, "equipArmor", stack);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return Optional.of(InteractionResult.CONSUME);
                } else {
                    return Optional.of(InteractionResult.SUCCESS);
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<InteractionResult> equipMoaChest(Entity target, Player player, ItemStack stack) {
        if (target instanceof Moa moa && moa.isPlayerGrown() && !moa.isBaby() && !moa.isVehicle()) {
            if (!stack.isEmpty() && stack.is(Items.CHEST) && !player.isSecondaryUseActive() && !moa.getData(ProtectDataAttachments.MOA_ARMOR).hasChest()) {
                if (!player.level().isClientSide()) {
                    moa.getData(ProtectDataAttachments.MOA_ARMOR).setSynched(moa.getId(), INBTSynchable.Direction.CLIENT, "setChest", true);
                    moa.playSound(ProtectSoundEvents.ENTITY_MOA_CHEST.get(), 1.0F, (moa.getRandom().nextFloat() - moa.getRandom().nextFloat()) * 0.2F + 1.0F);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    moa.getData(ProtectDataAttachments.MOA_ARMOR).createInventory();
                    return Optional.of(InteractionResult.CONSUME);
                } else {
                    return Optional.of(InteractionResult.SUCCESS);
                }
            }
        }
        return Optional.empty();
    }

    public static void onJoinLevel(Entity entity) {
        if (entity instanceof Moa moa) {
            moa.getData(ProtectDataAttachments.MOA_ARMOR).onJoinLevel();
        }
    }

    public static void onUpdate(Entity entity) {
        if (entity instanceof Moa moa) {
            moa.getData(ProtectDataAttachments.MOA_ARMOR).onUpdate();
        }
    }

    public static void onDrops(Entity entity, Collection<ItemEntity> drops) {
        if (entity instanceof Moa moa) {
            Container inventory = moa.getData(ProtectDataAttachments.MOA_ARMOR).getInventory();
            if (moa.getData(ProtectDataAttachments.MOA_ARMOR).hasChest()) {
                if (!moa.level().isClientSide()) {
                    ItemEntity itemEntity = new ItemEntity(moa.level(), moa.getX(), moa.getY() + (double) 0.0F, moa.getZ(), new ItemStack(Blocks.CHEST));
                    itemEntity.setDefaultPickUpDelay();
                    drops.add(itemEntity);
                }
                moa.getData(ProtectDataAttachments.MOA_ARMOR).setChest(false);
            }
            if (inventory != null) {
                drops.removeIf(itemEntity -> itemEntity.getItem().is(Items.SADDLE));
                for (int i = 0; i < inventory.getContainerSize(); ++i) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (!itemStack.isEmpty() && EnchantmentHelper.getTagEnchantmentLevel((Holder<Enchantment>) Enchantments.VANISHING_CURSE, itemStack) <= 0) {
                        ItemEntity itemEntity = new ItemEntity(moa.level(), moa.getX(), moa.getY() + (double) 0.0F, moa.getZ(), itemStack);
                        itemEntity.setDefaultPickUpDelay();
                        drops.add(itemEntity);
                    }
                }
            }
        }
    }
}
