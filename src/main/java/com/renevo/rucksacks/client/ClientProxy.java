package com.renevo.rucksacks.client;

import com.renevo.rucksacks.Util;
import com.renevo.rucksacks.common.CommonProxy;
import com.renevo.rucksacks.item.ItemRucksack;
import com.renevo.rucksacks.item.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Items.rucksack_ender, 0, new ModelResourceLocation(Util.getResource("rucksack_ender"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.rucksack, 0, new ModelResourceLocation(Util.getResource("rucksack"), "inventory"));
    }

    @Override
    public void registerColors() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack itemStack, int i) {
                return i > 0 ? 16777215 :((ItemRucksack)itemStack.getItem()).getColor(itemStack);
            }
        }, Items.rucksack);
    }
}
