package com.geodesictriangle.texturizer.proxy;


import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	@Override
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void render() 
	{
		//RenderingRegistry.registerEntityRenderingHandler(EntityMagiball.class, new RenderSnowball<EntityMagiball>(Minecraft.getMinecraft().getRenderManager(), ItemInit.MAGIBALL, Minecraft.getMinecraft().getRenderItem()));
	}
	
	@Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule)
	{
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }
	
    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().player;
    }



}