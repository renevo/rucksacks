package com.renevo.rucksacks.inventory;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@ChestContainer(rowSize = 9, isLargeChest = false)
public class ContainerRucksack extends Container {
    private InventoryRucksack rucksackInventory;
    private int numRows;

    public ContainerRucksack(EntityPlayer player, InventoryRucksack rucksackInventory) {
        this.rucksackInventory = rucksackInventory;
        this.numRows = rucksackInventory.getSizeInventory() / 9;

        rucksackInventory.openInventory(player);
        IInventory invPlayer = player.inventory;

        int lvt_4_1_ = (this.numRows - 4) * 18;

        int lvt_5_3_;
        int lvt_6_2_;
        for (lvt_5_3_ = 0; lvt_5_3_ < this.numRows; ++lvt_5_3_) {
            for (lvt_6_2_ = 0; lvt_6_2_ < 9; ++lvt_6_2_) {
                this.addSlotToContainer(new RucksackSlot(rucksackInventory, lvt_6_2_ + lvt_5_3_ * 9, 8 + lvt_6_2_ * 18, 18 + lvt_5_3_ * 18));
            }
        }

        for (lvt_5_3_ = 0; lvt_5_3_ < 3; ++lvt_5_3_) {
            for (lvt_6_2_ = 0; lvt_6_2_ < 9; ++lvt_6_2_) {
                ItemStack currentStack = invPlayer.getStackInSlot(lvt_6_2_ + lvt_5_3_ * 9 + 9);
                if (!currentStack.isEmpty()) {
                    NBTTagCompound stackNBT = currentStack.getTagCompound();
                    NBTTagCompound sackNBT = this.rucksackInventory.getRucksack().getTagCompound();
                    if (stackNBT != null && sackNBT != null && sackNBT.getInteger("cid") == stackNBT.getInteger("cid")) {
                        this.addSlotToContainer(new ReadOnlySlot(invPlayer, lvt_6_2_ + lvt_5_3_ * 9 + 9, 8 + lvt_6_2_ * 18, 103 + lvt_5_3_ * 18 + lvt_4_1_));
                        continue;
                    }
                }

                this.addSlotToContainer(new Slot(invPlayer, lvt_6_2_ + lvt_5_3_ * 9 + 9, 8 + lvt_6_2_ * 18, 103 + lvt_5_3_ * 18 + lvt_4_1_));
            }
        }

        for (lvt_5_3_ = 0; lvt_5_3_ < 9; ++lvt_5_3_) {
            ItemStack currentStack = invPlayer.getStackInSlot(lvt_5_3_);
            if (!currentStack.isEmpty()) {
                NBTTagCompound stackNBT = currentStack.getTagCompound();
                NBTTagCompound sackNBT = this.rucksackInventory.getRucksack().getTagCompound();
                if (stackNBT != null && sackNBT != null && sackNBT.getInteger("cid") == stackNBT.getInteger("cid")) {
                    this.addSlotToContainer(new ReadOnlySlot(invPlayer, lvt_5_3_, 8 + lvt_5_3_ * 18, 161 + lvt_4_1_));
                    continue;
                }
            }

            this.addSlotToContainer(new Slot(invPlayer, lvt_5_3_, 8 + lvt_5_3_ * 18, 161 + lvt_4_1_));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.rucksackInventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack lvt_3_1_ = ItemStack.EMPTY;
        Slot lvt_4_1_ = this.inventorySlots.get(slotIndex);
        if (lvt_4_1_ != null && lvt_4_1_.getHasStack()) {
            ItemStack lvt_5_1_ = lvt_4_1_.getStack();
            lvt_3_1_ = lvt_5_1_.copy();
            if (slotIndex < this.numRows * 9) {
                if (!this.mergeItemStack(lvt_5_1_, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(lvt_5_1_, 0, this.numRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (lvt_5_1_.getCount() == 0) {
                lvt_4_1_.putStack(ItemStack.EMPTY);
            } else {
                lvt_4_1_.onSlotChanged();
            }
        }

        return lvt_3_1_;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.rucksackInventory.closeInventory(entityPlayer);
    }
}