package com.geodesictriangle.texturizer.util.handlers;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.items.GuiWand;
import com.geodesictriangle.texturizer.objects.items.WandContainer;
import com.geodesictriangle.texturizer.objects.items.catwand.CatContainer;
import com.geodesictriangle.texturizer.objects.items.catwand.GuiCat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == 2)
		{
			return new WandContainer(player.inventory, player.getHeldItemMainhand(),player);
		}

		if(ID == 3)
		{
			return new CatContainer(player.inventory, player.getHeldItemMainhand(),player);
		}

    
		
	
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == 2)
		{
			return new GuiWand(player.inventory, player.getHeldItemMainhand(),player);
		}

		if(ID == 3)
		{
			return new GuiCat(player.inventory, player.getHeldItemMainhand(),player);
		}
				return null;
	}
}
