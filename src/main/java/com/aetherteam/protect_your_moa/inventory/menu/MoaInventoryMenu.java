package com.aetherteam.protect_your_moa.inventory.menu;

import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MoaInventoryMenu extends AbstractContainerMenu {
    private final Container moaContainer;
    private final Moa moa;

    public MoaInventoryMenu(int containerId, Inventory playerInventory, Container container, final Moa moa) {
        super(null, containerId);
        this.moaContainer = container;
        this.moa = moa;
        container.startOpen(playerInventory.player);
        this.addSlot(new Slot(container, 0, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(Items.SADDLE) && !this.hasItem() && moa.isSaddleable();
            }

            @Override
            public boolean isActive() {
                return moa.isSaddleable();
            }
        });
        this.addSlot(new Slot(container, 1, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.getItem() instanceof MoaArmorItem;
            }

            @Override
            public boolean isActive() {
                return moa.isSaddleable();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void setByPlayer(ItemStack stack) {
                if (!stack.isEmpty() && stack.getItem() instanceof MoaArmorItem moaArmorItem) {
                    moaArmorItem.onEquip(moa, stack);
                }
                super.setByPlayer(stack);
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if (stack.getItem() instanceof MoaArmorItem moaArmorItem) {
                    moaArmorItem.onUnequip(moa, stack);
                }
                super.onTake(player, stack);
            }
        });
        if (this.hasChest(moa)) {
            for (int k = 0; k < 3; ++k) {
                for (int l = 0; l < 5; ++l) {
                    this.addSlot(new Slot(container, 2 + l + k * 5, 80 + l * 18, 18 + k * 18));
                }
            }
        }

        for (int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
        }
    }

    public boolean stillValid(Player player) {
        return MoaArmor.get(this.moa).isPresent() && MoaArmor.get(this.moa).resolve().isPresent() && MoaArmor.get(this.moa).resolve().get().getInventory() == this.moaContainer && this.moaContainer.stillValid(player) && this.moa.isAlive() && this.moa.distanceTo(player) < 8.0F;
    }

    private boolean hasChest(Moa moa) {
        return MoaArmor.get(moa).isPresent() && MoaArmor.get(moa).resolve().isPresent() && MoaArmor.get(moa).resolve().get().hasChest();
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            int i = this.moaContainer.getContainerSize();
            if (index < i) {
                if (!this.moveItemStackTo(itemStack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemStack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemStack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemStack1)) {
                if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.moveItemStackTo(itemStack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.moveItemStackTo(itemStack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.moveItemStackTo(itemStack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemStack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player player) {
        super.removed(player);
        this.moaContainer.stopOpen(player);
    }
}
