package com.renevo.rucksacks;

import com.renevo.rucksacks.item.Items;

import com.renevo.rucksacks.item.crafting.RecipeRucksackDyes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public final class RucksacksRegistry {
    private RucksacksRegistry() {
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new ItemStack(Items.rucksack_ender),
                "#s#", "scs", "#s#",
                '#', net.minecraft.init.Items.LEATHER,
                's', net.minecraft.init.Items.STRING,
                'c', Blocks.ENDER_CHEST);
        GameRegistry.addRecipe(new ItemStack(Items.rucksack_ender),
                "s#s", "#c#", "s#s",
                '#', net.minecraft.init.Items.LEATHER,
                's', net.minecraft.init.Items.STRING,
                'c', Blocks.ENDER_CHEST);

        GameRegistry.addRecipe(new ItemStack(Items.rucksack),
                "#s#", "scs", "#s#",
                '#', net.minecraft.init.Items.LEATHER,
                's', net.minecraft.init.Items.STRING,
                'c', Blocks.CHEST);
        GameRegistry.addRecipe(new ItemStack(Items.rucksack),
                "s#s", "#c#", "s#s",
                '#', net.minecraft.init.Items.LEATHER,
                's', net.minecraft.init.Items.STRING,
                'c', Blocks.CHEST);

        // new forge 13 requirement to register sorting...
        RecipeSorter.register("rucksacks:sacks", RecipeRucksackDyes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeRucksackDyes());
    }
}
