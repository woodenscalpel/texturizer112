package com.geodesictriangle.texturizer.objects.items;

import java.util.List;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.init.ItemInit;
import com.geodesictriangle.texturizer.util.helpers.DestroyHelper;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

@MethodsReturnNonnullByDefault
public class PathWand extends AbstractSwapWand {
	

	public PathWand(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Texturizer.TEXTURIZERTAB);
		
		ItemInit.ITEMS.add(this);
	}
	
	
	public void registerModels() {
		Texturizer.proxy.registerItemRenderer(this, 0, "inventory");
	}

	   @Override
	    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	    {
	    
	    	
	        ItemStack stack = player.getHeldItem(hand);


        if (!world.isRemote) {



                for(int x = pos.getX()-1; x <= pos.getX()+1; x++){
                        for(int z = pos.getZ()-1; z <= pos.getZ()+1; z++) {
                            BlockPos swappos = new BlockPos(x, pos.getY(), z);
                            if(isSwapTarget(world,swappos)){
                            	Tuple<Block,Integer> nextBlock = getNextBlock(stack,player);
                                if (nextBlock.getFirst() != Blocks.AIR){
                                    swapBlock(world,swappos,!player.isCreative(),nextBlock);
                                    //Update Clients
                                    if(!world.isRemote)
                                        ((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world,swappos));
                                }
                            }
                        }
                }




            }
        return EnumActionResult.PASS;
    }

    public Tuple<Block,Integer> getNextBlock(ItemStack stack, EntityPlayer player){
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
        Integer mode = nbt.getInteger("mode");
        List<Tuple<Block,Integer>> blocklist = getBlockList(player, stack);

        if(blocklist.size() > 0) {
            int randomblock = (int) (Math.random() * blocklist.size());
            Tuple<Block,Integer> block = blocklist.get(randomblock);

            if(block.getFirst() != Blocks.AIR){
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
                    player.sendMessage(new TextComponentString("Ran Out of "+ Item.getItemFromBlock(block.getFirst()).getUnlocalizedName() ));
                    return new Tuple<Block,Integer>(Blocks.AIR,0);
                }
                return block;
            }

        }
        return new Tuple<Block,Integer>(Blocks.AIR,0);
        //randomblock = 1;
        //return Blocks.STONE;

    }

    public void swapBlock(World world, BlockPos pos, boolean dropBlock, Tuple<Block,Integer> swapblock){
        if(!world.isRemote) {
            DestroyHelper.destroyBlockNoParticles(pos,dropBlock, world);
            //world.destroyBlock(pos, dropBlock);
            world.setBlockState(pos, swapblock.getFirst().getStateFromMeta(swapblock.getSecond()));
        }
    }
    @Override
    boolean isSwapTarget(World world, BlockPos pos){
        return((world.getBlockState(pos).getBlock() != Blocks.AIR) && (world.getBlockState(pos).getBlock() != Blocks.BEDROCK) && !(world.getBlockState(pos).getBlock() instanceof BlockBush));
    }
    
}