package com.renevo.rucksacks.common;

import com.renevo.rucksacks.RucksacksRegistry;
import com.renevo.rucksacks.item.Items;

public class CommonProxy {

    public void preInit() {
        Items.init();

        registerModels();
    }

    public void init() {
        RucksacksRegistry.registerRecipes();
    }

    public void postInit() {

    }

    public void registerModels() {

    }
}
