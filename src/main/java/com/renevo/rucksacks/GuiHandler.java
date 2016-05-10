package com.renevo.rucksacks;

import com.renevo.rucksacks.client.gui.inventory.GuiRucksack;
import com.renevo.rucksacks.inventory.ContainerRucksack;
import com.renevo.rucksacks.inventory.InventoryRucksack;
import com.renevo.rucksacks.item.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_RUCKSACK = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_RUCKSACK) {
            return new ContainerRucksack(player, new InventoryRucksack(player, getRucksack(player)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_RUCKSACK) {
            return new GuiRucksack(player, new InventoryRucksack(player, getRucksack(player)));
        }

        return null;
    }

    private ItemStack getRucksack(EntityPlayer player) {
        // try to test for mainhand first, if in offhand, it doesn't matter
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == Items.rucksack) {
            return player.getHeldItemMainhand();
        }

        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() == Items.rucksack) {
            return player.getHeldItemOffhand();
        }

        return null;
    }
}
