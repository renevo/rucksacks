package com.renevo.rucksacks.item;

import com.renevo.rucksacks.Util;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class Items {
    private Items() {}

    public static Item rucksack_ender;
    public static Item rucksack;

    public static void init() {
        GameRegistry.registerItem(rucksack_ender = new ItemEnderRucksack().setUnlocalizedName(Util.prefix("rucksack_ender")), "rucksack_ender");
        GameRegistry.registerItem(rucksack = new ItemRucksack().setUnlocalizedName(Util.prefix("rucksack")), "rucksack");
    }
}
