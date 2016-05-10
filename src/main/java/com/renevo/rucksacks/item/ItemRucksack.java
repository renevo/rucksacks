package com.renevo.rucksacks.item;

import com.renevo.rucksacks.GuiHandler;
import com.renevo.rucksacks.Rucksacks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRucksack extends ItemMeta {

    public ItemRucksack() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            player.openGui(Rucksacks.instance, GuiHandler.GUI_RUCKSACK, world, 0, 0, 0);
            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
        }

        return ActionResult.newResult(EnumActionResult.PASS, itemStack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("display", 10)) {
            NBTTagCompound display = itemStack.getTagCompound().getCompoundTag("display");
            if (display.hasKey("Name")) {
                return display.getString("Name");
            }
        }

        return super.getItemStackDisplayName(itemStack);
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
