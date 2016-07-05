package com.renevo.rucksacks;

import com.renevo.rucksacks.common.CommonProxy;
import com.renevo.rucksacks.common.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Util.MODID,
        dependencies = "required-after:Forge@[12.18.0,);",
        acceptedMinecraftVersions = "1.10",
        updateJSON = "https://raw.githubusercontent.com/RenEvo/rucksacks/master/update.json",
        useMetadata = true)
public class Rucksacks {

    public Logger log;

    @SidedProxy(clientSide="com.renevo.rucksacks.client.ClientProxy", serverSide="com.renevo.rucksacks.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Util.MODID)
    public static Rucksacks instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        Config.loadConfiguration(event.getSuggestedConfigurationFile());
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}