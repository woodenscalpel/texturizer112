package com.geodesictriangle.texturizer;



import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.Logger;

import com.geodesictriangle.texturizer.client.KeyBinds;
import com.geodesictriangle.texturizer.network.messages.MessageChangeNBT;
import com.geodesictriangle.texturizer.network.messages.MessageModeToggle;
import com.geodesictriangle.texturizer.network.messages.MessageOpenInv;
import com.geodesictriangle.texturizer.network.messages.MessageOpenInv2;
import com.geodesictriangle.texturizer.proxy.CommonProxy;
import com.geodesictriangle.texturizer.util.handlers.RegistryHandler;

@Mod(modid = Texturizer.MODID, name = Texturizer.NAME, version = Texturizer.VERSION)
public class Texturizer
{	
	
	public static boolean chiselsandbitsloaded;

	  public Texturizer() {
		    if(Loader.isModLoaded("chiselsandbits")) {
		  		chiselsandbitsloaded = true;
		    }
		    else {
				  chiselsandbitsloaded = false;
		    }
		  }
	
	public static SimpleNetworkWrapper network;


    public static final String MODID = "texturizer";
    public static final String NAME = "Texturizer";
    public static final String VERSION = "1.2.0";
    public static final String CLIENT_PROXY_CLASS = "com.geodesictriangle.texturizer.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.geodesictriangle.texturizer.proxy.CommonProxy";
	
	
	public static final CreativeTabs TEXTURIZERTAB = new TexturizerTab("tutorialtab");
	
	@Instance
	public static Texturizer instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	

    public static Logger logger;


    
    
  
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	     logger = event.getModLog();
	     proxy.preInit(event);
	     RegistryHandler.preInitRegistries(event);
			Texturizer.network = NetworkRegistry.INSTANCE.newSimpleChannel(Texturizer.MODID);
			Texturizer.network.registerMessage(MessageModeToggle.class, MessageModeToggle.class, 0, Side.SERVER);
			Texturizer.network.registerMessage(MessageOpenInv.class, MessageOpenInv.class, 1, Side.SERVER);
			Texturizer.network.registerMessage(MessageOpenInv2.class, MessageOpenInv2.class, 2, Side.SERVER);
			Texturizer.network.registerMessage(MessageChangeNBT.class, MessageChangeNBT.class, 3, Side.SERVER);
			
			if(event.getSide().isClient()) {
		        OBJLoader.INSTANCE.addDomain(Texturizer.MODID);
			    }

/*
		RegistryHandler.preInitRegistries(event);
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Texturizer.MODID);
		network.registerMessage(MessageModeToggle.class, MessageModeToggle.class, 0, Side.SERVER);
		network.registerMessage(MessageOpenInv.class, MessageOpenInv.class, 1, Side.SERVER);
*/
		
		//network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		//network.registerMessage(PacketUpdateEnergy.Handler.class, PacketUpdateEnergy.class, 0, Side.CLIENT);
		//network.registerMessage(PacketRequestUpdateEnergy.Handler.class, PacketRequestUpdateEnergy.class, 1, Side.SERVER);
	}
	
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{				
		RegistryHandler.initRegistries();
		if(event.getSide().isClient()) {
			KeyBinds.init();
		    }
		 
	}
	

	


        

	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		RegistryHandler.postInitRegistries();
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}
	

}






