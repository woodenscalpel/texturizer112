package com.geodesictriangle.texturizer.ChiselAndBitsIntegration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.blocks.GuideBlock;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import mod.chiselsandbits.api.ChiselsAndBitsAddon;
import mod.chiselsandbits.api.IBitAccess;
import mod.chiselsandbits.api.IBitBrush;
import mod.chiselsandbits.api.IChiselAndBitsAPI;
import mod.chiselsandbits.api.IChiselsAndBitsAddon;
import mod.chiselsandbits.api.ItemType;
import mod.chiselsandbits.api.APIExceptions.CannotBeChiseled;
import mod.chiselsandbits.api.APIExceptions.InvalidBitItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@ChiselsAndBitsAddon
public class ChiselBitsAddon implements IChiselsAndBitsAddon{
 public static IChiselAndBitsAPI api;
public ChiselBitsAddon(){

 }
 public void onReadyChiselsAndBits(final IChiselAndBitsAPI api ) {
	 this.api = api;
}
 
 
 public void placeBits(GuideTile tile, World world, BlockPos pos) {
	 
		//HashSet<BlockPos> commitChangesList = new HashSet<>();

        try {
        	int scale = tile.BIT_SIZE;
        	
        	BlockPos microoffset = tile.originOffset.uoriginOffset;
    

			

			int rbitPosX = pos.getX()*scale%16+microoffset.getX();
        	int rbitPosY = pos.getY()*scale%16+microoffset.getY();
        	int rbitPosZ = pos.getZ()*scale%16+microoffset.getZ();
        	
        	int rbigPosX = pos.getX()*scale/16;
        	int rbigPosY = pos.getY()*scale/16;
        	int rbigPosZ = pos.getZ()*scale/16;
        
        	
            	
        	Tuple<BlockPos,BlockPos> rbitpos = fix(new Tuple<BlockPos,BlockPos>(new BlockPos(rbigPosX,rbigPosY,rbigPosZ),new BlockPos(rbitPosX,rbitPosY,rbitPosZ)));
        	Tuple<BlockPos,BlockPos> cornerbitpos = fix(rbitpos);
      
        
          	BlockPos cornerbigPos = cornerbitpos.getFirst();
        	BlockPos cornerbitPos = cornerbitpos.getSecond();
        	



        	
        	


 
		
         
	
         	
         	//make a bitsize by bitsize cube of bits
         	for(int x=0;x<(tile.BIT_SIZE);x++) {
             	for(int y=0;y<(tile.BIT_SIZE);y++) {
                 	for(int z=0;z<(tile.BIT_SIZE);z++) {
                 		
                 		Tuple<BlockPos,BlockPos> 	 bitpos = fix(new Tuple<BlockPos,BlockPos>(cornerbigPos,cornerbitPos.add(x, y, z)));

                 		
                 		BlockPos bigPos = bitpos.getFirst();
                 		//commitChangesList.add(bigPos);
                 		BlockPos bitPos = bitpos.getSecond();
                 		
                        BlockPos abspos = tile.getPos().add(bigPos.getX(),bigPos.getY(),bigPos.getZ());
                     	IBitAccess blockbits = ChiselBitsAddon.api.getBitAccess(world, abspos);

                     	if(blockbits.getBitAt(bitPos.getX(), bitPos.getY(), bitPos.getZ()).getState() == null) {
                     	
            			IBitBrush bit = getAndRemoveInvBit(tile,world);

                     	
						blockbits.setBitAt(bitPos.getX(),bitPos.getY(),bitPos.getZ(),bit);
						blockbits.commitChanges(true);
                     	}
                   	}}}
         	
      
				
        
         }
     	catch(Exception e){
     		Texturizer.logger.info("Exception! {}", e);
     		//handling exceptings is for nerds :^)  
         }

     
    
 }
 
 Tuple<BlockPos,BlockPos> fix(Tuple<BlockPos,BlockPos> pos){
		
    	
 	    int bigPosX = pos.getFirst().getX();    
 	    int bigPosY = pos.getFirst().getY();    
 	    int bigPosZ = pos.getFirst().getZ();    


    	
    	int bitPosX = pos.getSecond().getX();
    	int bitPosY = pos.getSecond().getY();
    	int bitPosZ = pos.getSecond().getZ();
    	
    	
      	if(bitPosX < 0) {
    		bigPosX -= 1;
    		bitPosX +=16;
    	};

    	if(bitPosY < 0) {
    		bigPosY -= 1;
    		bitPosY +=16;

    	};    	
    	if(bitPosZ < 0) {
    		bigPosZ -= 1;
    		bitPosZ +=16;

    	};
    	
     	if(bitPosX > 15) {
    		bigPosX += 1;
    		bitPosX -=16;
    	};

    	if(bitPosY > 15) {
    		bigPosY += 1;
    		bitPosY -=16;

    	};    	
    	if(bitPosZ > 15) {
    		bigPosZ += 1;
    		bitPosZ -=16;

    	};
		
    	
    	
    	return new Tuple<BlockPos,BlockPos>(new BlockPos(bigPosX,bigPosY,bigPosZ),new BlockPos(bitPosX,bitPosY,bitPosZ));

 }
 
 public IBitBrush getAndRemoveInvBit(GuideTile tile, World world) throws InvalidBitItem{
     BlockPos tilePos = tile.getPos();
     EnumFacing facing =  Objects.requireNonNull(world).getBlockState(tilePos).getValue(GuideBlock.FACING);
     BlockPos invPos;

     switch (facing){

         case NORTH:
         default:
              invPos = tilePos.add(0,0,1);
             break;
         case EAST:
              invPos = tilePos.add(-1,0,0);
             break;
         case SOUTH:
              invPos = tilePos.add(0,0,-1);
             break;
         case WEST:
              invPos = tilePos.add(1,0,0);
             break;

     }
    // if(this.getWorld().getBlockState(invPos).hasTileEntity()){
         TileEntity potentialContainer = world.getTileEntity(invPos);
         if(potentialContainer != null) {
         IItemHandler inv = potentialContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
         if(inv != null){
             for(int slot  = 0; slot<inv.getSlots();slot++){
                 Texturizer.logger.info("block {} found in slot {}",ChiselBitsAddon.api.getItemType(inv.getStackInSlot(slot)),slot);
           
                 if(ChiselBitsAddon.api.getItemType(inv.getStackInSlot(slot)) == ItemType.CHISLED_BIT){
                     Texturizer.logger.info("block {} found in slot {}",inv.getStackInSlot(slot),slot);
                     try {
                     IBitBrush bit = ChiselBitsAddon.api.createBrush((inv.getStackInSlot(slot)));
                     inv.getStackInSlot(slot).shrink(1);
                     return bit;
                     }catch(Exception e){}

                 }
                 if(ChiselBitsAddon.api.getItemType(inv.getStackInSlot(slot)) == ItemType.BIT_BAG){
                 IItemHandler bag = inv.getStackInSlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
                 for(int bagslot  = 0; bagslot<bag.getSlots();bagslot++){
                     Texturizer.logger.info("block {} found in slot {}",ChiselBitsAddon.api.getItemType(inv.getStackInSlot(slot)),slot);
               
                     if(ChiselBitsAddon.api.getItemType(bag.getStackInSlot(bagslot)) == ItemType.CHISLED_BIT){
                         Texturizer.logger.info("block {} found in slot {}",bag.getStackInSlot(bagslot),bagslot);
                         try {
                         IBitBrush bit = ChiselBitsAddon.api.createBrush((bag.getStackInSlot(bagslot)));
                         bag.extractItem(bagslot, 1, false);
                         return bit;
                         }catch(Exception e){}
                         //compile you fuck

                     }

             }
         }
         }

     //}
 }
}
         return ChiselBitsAddon.api.createBrush(ItemStack.EMPTY);
 }
}
