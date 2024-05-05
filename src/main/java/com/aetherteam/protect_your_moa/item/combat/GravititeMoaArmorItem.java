package com.aetherteam.protect_your_moa.item.combat;

import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GravititeMoaArmorItem extends MoaArmorItem {
    public GravititeMoaArmorItem() {
        super(11, "gravitite", new Item.Properties().stacksTo(1));
    }

    @Override
    public void onUnequip(Moa moa, ItemStack itemStack) {
        if (moa.getRemainingJumps() > moa.getMaxJumps()) {
            moa.setRemainingJumps(moa.getMaxJumps());
        }
        super.onUnequip(moa, itemStack);
    }
}
