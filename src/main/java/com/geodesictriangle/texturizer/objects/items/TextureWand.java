package com.geodesictriangle.texturizer.objects.items;


import java.util.List;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.init.ItemInit;

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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TextureWand extends AbstractSwapWand {

	public TextureWand(String name) 
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
    Integer SELECTING_CORNER_ONE = 0;
    Integer SELECTING_CORNER_TWO = 1;
    Integer SWAPPING = 2;

    //Available Modes
    Integer HOTBAR = 0;
    Integer INTERNAL = 1;

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
                        //state = SELECTING_CORNER_ONE;
                        nbt.setInteger("state",SELECTING_CORNER_ONE);
                        return EnumActionResult.PASS;
                    }
            }

            // Corners are set
            assert player != null;
            if (nbt.getBoolean("corner2Valid") && !player.isSneaking()) {
                if(withinArea(stack,pos)){

                    swapArea(player, world,  pos,hand,  facing,hitX, hitY, hitZ); //TODO LOTS of side effects and state changes in this function, should be broken up
                }

            }



        }
        return EnumActionResult.PASS;
    }









    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {
    


        if(!world.isRemote) {

               
        		EntityPlayer player = (EntityPlayer) entity;
        		
        		  NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
                  Integer state = nbt.getInteger("state");
    

            if (state.equals(SWAPPING)) {


                //These always return valid, but will set the state to not swap if there are no valid targets left or if
                //This is not a good way to do this TODO fix this
                BlockPos nextPos = getNextSwapPos(world,stack);

                Tuple<Block, Integer> nextBlock = getNextBlock(stack,player);
            	


                setLastVisited(stack,nextPos);


               state = nbt.getInteger("state");


                if (state.equals(SWAPPING)) {

                    if(nextBlock.getFirst() != Blocks.AIR) { //Air block means skip this spot
                        swapBlock(world, nextPos, !player.isCreative(), nextBlock);
                    }

                }
                }

                }

    }

    public void swapBlock(World world, BlockPos pos, boolean dropBlock, Tuple<Block, Integer>swapblock){
        if(!world.isRemote) {
            //destroyBlockNoParticles(destroypos,!player.abilities.isCreativeMode,(Entity) null, world);

            world.destroyBlock(pos, dropBlock);
            world.setBlockState(pos, swapblock.getFirst().getStateFromMeta(swapblock.getSecond()));
        }
    }

    public Tuple<Block,Integer> getNextBlock(ItemStack stack, EntityPlayer player){
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
        Integer mode = nbt.getInteger("mode");
        List<Tuple<Block,Integer>> blocklist = getBlockList(player, stack);

        if(blocklist.size() > 0) {
            int randomblock = (int) (Math.random() * blocklist.size());
            Tuple<Block, Integer> block = blocklist.get(randomblock);
            
            if(!(block.getFirst() == Blocks.AIR)){
            if(player.isCreative()){
                return block;
            }

            boolean HasBlock = false;
        	Tuple<Item,Integer> item = new Tuple<Item,Integer>(Item.getItemFromBlock(block.getFirst()),block.getSecond());
            if(mode.equals(INTERNAL)){
                HasBlock = wandInvRemoveItem(stack, item); // Also removes item
            }
            if(mode.equals(HOTBAR) || !HasBlock) {
                HasBlock = playerInvRemoveItem(player, item); // Also removes item
            }

            if(!HasBlock){
                nbt.setInteger("state",SELECTING_CORNER_ONE);             
                player.sendMessage(new TextComponentString("Ran Out of "+ Item.getItemFromBlock(block.getFirst()).getUnlocalizedName() ));
            }
                return block;
            }

        }
        return new Tuple<Block,Integer>(Blocks.AIR,0);
        //randomblock = 1;
        //return Blocks.STONE;

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
        setSwapStart(stack);
    }
    public void setLastVisited(ItemStack stack, BlockPos pos){
   	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

        nbt.setInteger("lastX", pos.getX());
        nbt.setInteger("lastY", pos.getY());
        nbt.setInteger("lastZ", pos.getZ());
    }

    public void setSwapStart(ItemStack stack){
      	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

        int x1 = nbt.getInteger("corner1x");
        int y1 = nbt.getInteger("corner1y");
        int z1 = nbt.getInteger("corner1z");
        int x2 = nbt.getInteger("corner2x");
        int y2 = nbt.getInteger("corner2y");
        int z2 = nbt.getInteger("corner2z");
        AxisAlignedBB area = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);


        //Set position to start checking for swapping
        nbt.setInteger("lastX", (int) area.minX -1);
        nbt.setInteger("lastY", (int) area.minY);
        nbt.setInteger("lastZ", (int) area.minZ);
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

    public void swapArea(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack stack = player.getHeldItem(hand);

   	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

     int x1 = nbt.getInteger("corner1x");
     int y1 = nbt.getInteger("corner1y");
     int z1 = nbt.getInteger("corner1z");
     int x2 = nbt.getInteger("corner2x");
     int y2 = nbt.getInteger("corner2y");
     int z2 = nbt.getInteger("corner2z");


        AxisAlignedBB area = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        //Check to see if more than 1 million blocks are selected.
        //TODO This is probably not where this check should be. Move it in with the other checks
        if ((area.maxX - area.minX) * (area.maxY - area.minY) * (area.maxZ - area.minZ) > MAXIMUM_BLOCKS) {
            assert player != null;
            player.sendMessage(new TextComponentString("Selected Area is Too Large. Aborting"));

        }
        else
            {

            //state = SWAPPING;
            nbt.setInteger("state", SWAPPING);

        	

            }
    }

    BlockPos getNextSwapPos(World world,ItemStack stack){
      	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

        int lastXVisited = nbt.getInteger("lastX");
        int lastYVisited = nbt.getInteger("lastY");
        int lastZVisited = nbt.getInteger("lastZ");
        int x1 = nbt.getInteger("corner1x");
        int y1 = nbt.getInteger("corner1y");
        int z1 = nbt.getInteger("corner1z");
        int x2 = nbt.getInteger("corner2x");
        int y2 = nbt.getInteger("corner2y");
        int z2 = nbt.getInteger("corner2z");
        AxisAlignedBB area = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);


           int z = lastZVisited;
           int y = lastYVisited;
          int  x = lastXVisited + 1;


        //for (int z = (int) area.minZ; z <= area.maxZ; z++) {
        while( z <= area.maxZ){
            while( y <= area.maxY){
                while( x <= area.maxX){







                    BlockPos pos = new BlockPos(x,y,z);
            if (isSwapTarget(world, pos)) {
                return pos;
            }
            x++;
            }
                y++;
                x = (int) area.minX;
                }
                    z++;
                    y= (int) area.minY;
                        }

        nbt.setInteger("state",SELECTING_CORNER_ONE);
        return new BlockPos((int) area.minX -1,(int) area.minY, (int) area.minZ); //Starts it at the beginning for next time

    }
    public boolean withinArea(ItemStack stack, BlockPos pos){
     	 NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");

    	   int x1 = nbt.getInteger("corner1x");
           int y1 = nbt.getInteger("corner1y");
           int z1 = nbt.getInteger("corner1z");
           int x2 = nbt.getInteger("corner2x");
           int y2 = nbt.getInteger("corner2y");
           int z2 = nbt.getInteger("corner2z");
        boolean ValidArea = nbt.getBoolean("corner2Valid");
        AxisAlignedBB area = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);

        return(ValidArea && area.minX <= pos.getX() && pos.getX() <= area.maxX && area.minY <= pos.getY() && pos.getY() <= area.maxY && area.minZ <= pos.getZ() && pos.getZ() <= area.maxZ);
    }


	
}
