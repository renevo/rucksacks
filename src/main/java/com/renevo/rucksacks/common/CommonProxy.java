package com.renevo.rucksacks.common;

import com.renevo.rucksacks.GuiHandler;
import com.renevo.rucksacks.Rucksacks;
import com.renevo.rucksacks.RucksacksRegistry;
import com.renevo.rucksacks.item.Items;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit() {
        Items.init();

        registerModels();
    }

    public void init() {
        RucksacksRegistry.registerRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(Rucksacks.instance, new GuiHandler());
    }

    public void postInit() {

        registerColors();
    }

    public void registerModels() {

    }

    public void registerColors() {

    }
}
