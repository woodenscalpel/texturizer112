package com.geodesictriangle.texturizer.init;

import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.objects.blocks.GuideBlock;
import com.geodesictriangle.texturizer.objects.blocks.GuideBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block GUIDE_BLOCK = new GuideBlock("guideblock", Material.GLASS);

	public static void getBlocks() {
		// TODO Auto-generated method stub
		
	}
	
}