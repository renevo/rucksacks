package com.renevo.rucksacks.item.crafting;

import com.google.common.collect.Lists;
import com.renevo.rucksacks.item.ItemRucksack;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;

public class RecipeRucksackDyes implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        ItemStack itemRucksack = null;
        ArrayList<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack current = inventory.getStackInSlot(i);
            if (!current.isEmpty()) {
                if (current.getItem() instanceof ItemRucksack) {
                    itemRucksack = current;
                } else {
                    if (current.getItem() != Items.DYE) {
                        return false;
                    }

                    list.add(current);
                }
            }
        }

        return itemRucksack != null && !list.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack output = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemRucksack itemRucksack = null;

        int i1;
        int k1;
        float f3;
        float f4;
        int var17;
        for (i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
            ItemStack j1 = inventory.getStackInSlot(i1);
            if (!j1.isEmpty()) {
                if (j1.getItem() instanceof ItemRucksack) {
                    itemRucksack = (ItemRucksack) j1.getItem();
                    if (output != null) {
                        return ItemStack.EMPTY;
                    }

                    output = j1.copy();
                    output.setCount(1);
                    if (itemRucksack.hasColor(j1)) {
                        k1 = itemRucksack.getColor(output);
                        f3 = (float) (k1 >> 16 & 255) / 255.0F;
                        f4 = (float) (k1 >> 8 & 255) / 255.0F;
                        float lvt_12_3_ = (float) (k1 & 255) / 255.0F;
                        i = (int) ((float) i + Math.max(f3, Math.max(f4, lvt_12_3_)) * 255.0F);
                        aint[0] = (int) ((float) aint[0] + f3 * 255.0F);
                        aint[1] = (int) ((float) aint[1] + f4 * 255.0F);
                        aint[2] = (int) ((float) aint[2] + lvt_12_3_ * 255.0F);
                        ++j;
                    }
                } else {
                    if (j1.getItem() != Items.DYE) {
                        return ItemStack.EMPTY;
                    }

                    float[] var14 = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(j1.getMetadata()));
                    int var15 = (int) (var14[0] * 255.0F);
                    int var16 = (int) (var14[1] * 255.0F);
                    var17 = (int) (var14[2] * 255.0F);
                    i += Math.max(var15, Math.max(var16, var17));
                    aint[0] += var15;
                    aint[1] += var16;
                    aint[2] += var17;
                    ++j;
                }
            }
        }

        if (itemRucksack == null) {
            return ItemStack.EMPTY;
        } else {
            i1 = aint[0] / j;
            int var13 = aint[1] / j;
            k1 = aint[2] / j;
            f3 = (float) i / (float) j;
            f4 = (float) Math.max(i1, Math.max(var13, k1));
            i1 = (int) ((float) i1 * f3 / f4);
            var13 = (int) ((float) var13 * f3 / f4);
            k1 = (int) ((float) k1 * f3 / f4);
            var17 = (i1 << 8) + var13;
            var17 = (var17 << 8) + k1;
            itemRucksack.setColor(output, var17);
            return output;
        }
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory) {
        int size = inventory.getSizeInventory();
        NonNullList<ItemStack> itemStacks = NonNullList.create();

        for (int i = 0; i < size; ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            itemStacks.add(i, ForgeHooks.getContainerItem(itemstack));
        }

        return itemStacks;
    }
}
