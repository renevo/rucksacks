package com.renevo.rucksacks.client;

import com.renevo.rucksacks.Util;
import com.renevo.rucksacks.common.CommonProxy;
import com.renevo.rucksacks.item.Items;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Items.rucksack_ender, 0, new ModelResourceLocation(Util.getResource("rucksack_ender"), "inventory"));
    }
}
