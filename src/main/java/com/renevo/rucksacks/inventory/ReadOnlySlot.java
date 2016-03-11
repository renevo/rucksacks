package com.renevo.rucksacks.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

// prevents putting/getting anything from this slot

public class ReadOnlySlot extends Slot {
    public ReadOnlySlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_canTakeStack_1_) {
        return false;
    }

}
