package com.geodesictriangle.texturizer.objects.items.catwand;


import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.init.ItemInit;
import com.geodesictriangle.texturizer.util.helpers.CatenaryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CatWand extends AbstractCatWand {

	public CatWand(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Texturizer.TEXTURIZERTAB);
		
		ItemInit.ITEMS.add(this);
	}
	/*
	public void registerModels() {
		Texturizer.proxy.registerItemRenderer(this, 0, "inventory");
	}
	*/
	
	


    //Available States
	public int BIT_SIZE = 16;
    Integer SELECTING_CORNER_ONE = 0;
    Integer SELECTING_CORNER_TWO = 1;

    Integer MAXIMUM_BLOCKS = 1000000;

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    
    	
        ItemStack stack = player.getHeldItem(hand);


        if (!world.isRemote) {

        	   NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
               Integer state = nbt.getInteger("state");




            if (player != null && player.isSneaking()) {
                    if (state == SELECTING_CORNER_ONE) {
                        player.sendMessage(new TextComponentString("Corner 1 set"));
                        setCorner1(stack,pos);
                        
                        nbt.setBoolean("corner2Valid",false);
                        //state = SELECTING_CORNER_TWO;
                        nbt.setInteger("state",SELECTING_CORNER_TWO);
                        return EnumActionResult.PASS;


                    } else if (state == SELECTING_CORNER_TWO) {
                        player.sendMessage(new TextComponentString("Corner 2 set"));
                        setCorner2(stack,pos);
                        nbt.setBoolean("corner2Valid",true);

                        BlockPos pos1 = this.getCorner1(stack);
                        double size = Math.sqrt(Math.pow((pos.getX() - pos1.getX()),2)+Math.pow((pos.getY() - pos1.getY()),2) + Math.pow((pos.getZ() - pos1.getZ()),2))*1.1;
                        nbt.setFloat("length",(float) size);
                        //state = SELECTING_CORNER_ONE;
                        nbt.setInteger("state",SELECTING_CORNER_ONE);
                        nbt.setInteger("bitsize",BIT_SIZE);
                        //Do math
                        return EnumActionResult.PASS;
                    }
            }

            // Corners are set
        }
        return EnumActionResult.PASS;
    }









    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {

    }


    public void setCorner1(ItemStack stack, BlockPos pos){
    	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

        nbt.setInteger("corner1x", pos.getX());
        nbt.setInteger("corner1y", pos.getY());
        nbt.setInteger("corner1z", pos.getZ());

    }
    public void setCorner2(ItemStack stack, BlockPos pos){
    	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

         nbt.setInteger("corner2x", pos.getX());
         nbt.setInteger("corner2y", pos.getY());
         nbt.setInteger("corner2z", pos.getZ());
    }

    public BlockPos getCorner1(ItemStack stack){
     	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

        int x = nbt.getInteger("corner1x");
        int y = nbt.getInteger("corner1y");
        int z = nbt.getInteger("corner1z");
        return new BlockPos(x,y,z);
    }
    public BlockPos getCorner2(ItemStack stack){
    	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
        int x = nbt.getInteger("corner2x");
        int y = nbt.getInteger("corner2y");
        int z = nbt.getInteger("corner2z");
        return new BlockPos(x,y,z);
    }









	public void pluslen(ItemStack stack) {
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
		float s = nbt.getFloat("length");
		nbt.setFloat("length",(float) (s+0.1));
	}

	public void minuslen(ItemStack stack) {
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
		float s = nbt.getFloat("length");
		nbt.setFloat("length",(float) (s-0.1));
	}









	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}









	public void plusbit(ItemStack stack) {
		// TODO Auto-generated method stub
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
		float bs = nbt.getFloat("bitsize");
		if (bs < 16){
		nbt.setFloat("bitsize",(float) (bs+1));
		}
		
	}
	public void minusbit(ItemStack stack) {
		// TODO Auto-generated method stub
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
		float bs = nbt.getFloat("bitsize");
		if (bs > 1) {
		nbt.setFloat("bitsize",(float) (bs-1));
		}
		
	}









	public void setlen(ItemStack stack, float val) {
		// TODO Auto-generated method stub
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
    	nbt.setFloat("length", val);
	}









	public void setbit(ItemStack stack, float val) {
		// TODO Auto-generated method stub
		
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
    	nbt.setFloat("bitsize", val);
	}
	
	public void setnbtval(ItemStack stack, String tag, float val) {
		// TODO Auto-generated method stub
		
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
    	nbt.setFloat(tag, val);
	}









	public void build(ItemStack heldItemMainhand) {
		// TODO Auto-generated method stub
		
	}


}
