package com.geodesictriangle.texturizer.proxy;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.network.messages.MessageModeToggle;
import com.geodesictriangle.texturizer.network.messages.MessageOpenInv;
import com.geodesictriangle.texturizer.util.handlers.RegistryHandler;
import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		
	}
	
	public void render()
	{
		
	}
	
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule)
	{
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer()
    {
        throw new IllegalStateException("This should only be called from client side");
    }

	public void preInit(FMLPreInitializationEvent event) {

			

	}


    
}
