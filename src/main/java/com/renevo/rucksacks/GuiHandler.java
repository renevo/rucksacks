package com.renevo.rucksacks;

import com.renevo.rucksacks.client.gui.inventory.GuiRucksack;
import com.renevo.rucksacks.inventory.ContainerRucksack;
import com.renevo.rucksacks.inventory.InventoryRucksack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_RUCKSACK = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_RUCKSACK) {
            return new ContainerRucksack(player, new InventoryRucksack(player, player.getHeldItem()));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_RUCKSACK) {
            return new GuiRucksack(player, new InventoryRucksack(player, player.getHeldItem()));
        }

        return null;
    }
}
