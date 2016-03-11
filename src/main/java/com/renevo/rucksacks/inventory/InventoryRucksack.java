package com.renevo.rucksacks.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class InventoryRucksack implements IInventory {

    private ItemStack rucksack;
    private EntityPlayer player;
    private ItemStack[] inventory;

    public InventoryRucksack(EntityPlayer player, ItemStack rucksack) {
        this.rucksack = rucksack;

        this.player = player;
        this.inventory = new ItemStack[this.getSizeInventory()];
        readFromNBT();
    }

    public ItemStack getRucksack() {
        return this.rucksack;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public void writeToNBT() {
        NBTTagCompound nbtTagCompound = this.rucksack.getTagCompound();
        if (nbtTagCompound == null) {
            this.rucksack.setTagCompound(nbtTagCompound = new NBTTagCompound());
        }

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound itemStackTag = new NBTTagCompound();
                itemStackTag.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(itemStackTag);
                tagList.appendTag(itemStackTag);
            }
        }

        nbtTagCompound.setTag("Items", tagList);

        this.rucksack.setTagCompound(nbtTagCompound);
    }

    public void readFromNBT() {
        NBTTagCompound nbtTagCompound = this.rucksack.getTagCompound();

        if (nbtTagCompound == null) {
            this.rucksack.setTagCompound(nbtTagCompound = new NBTTagCompound());
        }

        if (nbtTagCompound.hasKey("Items")) {
            NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
            this.inventory = new ItemStack[this.getSizeInventory()];
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound itemStackTag = tagList.getCompoundTagAt(i);
                int itemSlot = itemStackTag.getByte("Slot");
                if (itemSlot >= 0 && itemSlot < inventory.length) {
                    this.inventory[itemSlot] = ItemStack.loadItemStackFromNBT(itemStackTag);
                }
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return slotIndex < 0 || slotIndex >= this.getSizeInventory() ? null : this.inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return null;
        }

        if (inventory[slotIndex] != null) {
            ItemStack output;
            if (inventory[slotIndex].stackSize <= amount) {
                output = inventory[slotIndex];
                inventory[slotIndex] = null;
                return output;
            }

            output = inventory[slotIndex].splitStack(amount);
            if (inventory[slotIndex].stackSize <= 0) {
                inventory[slotIndex] = null;
            }
            return output;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return null;
        }

        ItemStack stack = null;
        if (inventory[slotIndex] != null) {
            stack = inventory[slotIndex];
            inventory[slotIndex] = null;
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return;
        }

        this.inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (itemStack != null && itemStack.stackSize == 0) {
            this.inventory[slotIndex] = null;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        // no-op
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {
        // not used
    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {
        this.writeToNBT();
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return itemStack != this.rucksack;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.inventory.length; i++) {
            this.inventory[i] = null;
        }
    }

    @Override
    public String getName() {
        return this.rucksack.getItem().getItemStackDisplayName(this.rucksack);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }
}
