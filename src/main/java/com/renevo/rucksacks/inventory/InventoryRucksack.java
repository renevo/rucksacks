package com.renevo.rucksacks.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;


import java.util.Random;

public class InventoryRucksack implements IInventory {

    private ItemStack rucksack;
    private EntityPlayer player;
    private ItemStack[] inventory;
    // TODO: should probably make this a notnulllist thingy
    // TODO: should probably switch to the basic inventory base class as well, that handles most of this shit

    public InventoryRucksack(EntityPlayer player, ItemStack rucksack) {
        this.rucksack = rucksack;

        this.player = player;
        this.inventory = new ItemStack[this.getSizeInventory()];
        readFromNBT();
    }

    public ItemStack getRucksack() {
        return this.rucksack;
    }

    public void writeToNBT() {
        if (this.player.world.isRemote) {
            return;
        }
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

        // update the player inventory (needed to actually save shit)
        for (int i = -1; i < this.player.inventory.getSizeInventory(); ++i) {
            ItemStack currentStack;

            if (i == -1) {
                currentStack = player.inventory.getItemStack();
            } else {
                currentStack = player.inventory.getStackInSlot(i);
            }

            if (!currentStack.isEmpty()) {
                NBTTagCompound stackNBT = currentStack.getTagCompound();
                if (stackNBT != null && this.rucksack.getTagCompound().getInteger("cid") == stackNBT.getInteger("cid")) {
                    this.rucksack.setCount(1);

                    if (i == -1) {
                        player.inventory.setItemStack(this.rucksack);
                    } else {
                        player.inventory.setInventorySlotContents(i, this.rucksack);
                    }

                    break;
                }
            }
        }
    }

    public void readFromNBT() {
        if (this.player.world.isRemote) {
            return;
        }

        NBTTagCompound nbtTagCompound = this.rucksack.getTagCompound();

        if (nbtTagCompound == null) {
            this.rucksack.setTagCompound(nbtTagCompound = new NBTTagCompound());
        }

        if (!nbtTagCompound.hasKey("Items")) {
            nbtTagCompound.setTag("Items", new NBTTagList());
        }

        nbtTagCompound.setInteger("cid", (new Random()).nextInt());

        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemStackTag = tagList.getCompoundTagAt(i);
            int itemSlot = itemStackTag.getByte("Slot");
            if (itemSlot >= 0 && itemSlot < inventory.length) {
                this.inventory[itemSlot] = new ItemStack(itemStackTag);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public boolean isEmpty() {
        // well, thats one way to do it...
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return slotIndex < 0 ||
                slotIndex >= this.getSizeInventory()
                ? ItemStack.EMPTY
                : this.inventory[slotIndex] == null
                ? ItemStack.EMPTY
                : this.inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return ItemStack.EMPTY;
        }

        if (inventory[slotIndex] != null && !inventory[slotIndex].isEmpty()) {
            ItemStack output;
            if (inventory[slotIndex].getCount() <= amount) {
                output = inventory[slotIndex];
                inventory[slotIndex] = ItemStack.EMPTY;
                return output;
            }

            output = inventory[slotIndex].splitStack(amount);
            if (inventory[slotIndex].isEmpty()) {
                inventory[slotIndex] = ItemStack.EMPTY;
            }
            return output;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = null;
        if (inventory[slotIndex] != null && !inventory[slotIndex].isEmpty()) {
            stack = inventory[slotIndex];
            inventory[slotIndex] = ItemStack.EMPTY;
        }
        return stack == null ? ItemStack.EMPTY : stack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        if (slotIndex < 0 || slotIndex >= this.getSizeInventory()) {
            return;
        }

        this.inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack != ItemStack.EMPTY && itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        if (itemStack != null && itemStack != ItemStack.EMPTY && itemStack.isEmpty()) {
            this.inventory[slotIndex] = ItemStack.EMPTY;
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
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
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
            this.inventory[i] = ItemStack.EMPTY;
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
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }
}
