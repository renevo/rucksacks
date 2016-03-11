package com.renevo.rucksacks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderRucksack extends ItemMeta {

    public ItemEnderRucksack() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            InventoryEnderChest enderChest = player.getInventoryEnderChest();
            player.displayGUIChest(enderChest);
        }
        return itemStack;
    }
}
