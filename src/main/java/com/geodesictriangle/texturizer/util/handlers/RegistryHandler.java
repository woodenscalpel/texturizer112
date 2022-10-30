package com.geodesictriangle.texturizer.util.handlers;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.init.BlockInit;
import com.geodesictriangle.texturizer.init.ItemInit;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;
import com.geodesictriangle.texturizer.objects.tileentities.RenderGuideTile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegister(ModelRegistryEvent event)
	{
		
		Texturizer.proxy.registerItemRenderer(Item.getItemFromBlock(BlockInit.GUIDE_BLOCK), 0, "inventory");	
		
		ClientRegistry.bindTileEntitySpecialRenderer(GuideTile.class, new RenderGuideTile());
		//RenderHandler.registerCustomMeshesAndStates();
		//RenderHandler.registerEntityRenders();
	
		for(Item item : ItemInit.ITEMS)
		{
			Texturizer.proxy.registerItemRenderer(item, 0, "inventory");
		}
		
		for(Block block : BlockInit.BLOCKS)
		{
			Texturizer.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
		
	}
	
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		GameRegistry.registerTileEntity(GuideTile.class, new ResourceLocation(Texturizer.MODID + ":guidetile"));
	}
	
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		
	/*
		ConfigHandler.registerConfig(event);
		*/
	}
	
	public static void initRegistries()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Texturizer.instance, new GuiHandler());

		/*
		SmeltingRecipes.init();
		CraftingRecipes.init();
		*/
		Texturizer.proxy.render();
	/*
		EnumHelper.addArt("Test", "Test", 16, 16, 112, 0);
		*/
	}
	
	public static void postInitRegistries()
	{
		
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{

	}
}
