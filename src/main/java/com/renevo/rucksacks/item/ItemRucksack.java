package com.renevo.rucksacks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRucksack extends ItemMeta {

    public ItemRucksack() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            // TODO: open inventory
        }
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int damage) {
        int i = this.getColor(itemStack);
        if (i < 0) {
            i = 16777215;
        }

        return i;
    }

    public boolean hasColor(ItemStack itemStack) {
        return (itemStack.hasTagCompound() && (itemStack.getTagCompound().hasKey("display", 10) && itemStack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    public int getColor(ItemStack itemStack) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt != null) {
            NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
            if (nbtDisplay != null && nbtDisplay.hasKey("color", 3)) {
                return nbtDisplay.getInteger("color");
            }
        }

        return 10511680;
    }

    public void removeColor(ItemStack itemStack) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt != null) {
            NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
            if (nbtDisplay.hasKey("color")) {
                nbtDisplay.removeTag("color");
            }
        }
    }

    public void setColor(ItemStack itemStack, int color) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            itemStack.setTagCompound(nbt);
        }

        NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
        if (!nbt.hasKey("display", 10)) {
            nbt.setTag("display", nbtDisplay);
        }

        nbtDisplay.setInteger("color", color);
    }
}
