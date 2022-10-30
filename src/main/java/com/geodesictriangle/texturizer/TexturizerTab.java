package com.geodesictriangle.texturizer;

import com.geodesictriangle.texturizer.init.ItemInit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TexturizerTab extends CreativeTabs
{
	public TexturizerTab(String label) 
	{
		super("Texturizer");
	//	this.setBackgroundImageName("tutorialmod.png");
	}
	
	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(ItemInit.TEXTURE_WAND);
	}
}

