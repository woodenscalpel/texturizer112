package com.geodesictriangle.texturizer.objects.items.catwand;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.html.parser.Entity;

import com.geodesictriangle.texturizer.Texturizer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractCatWand extends Item {

	AbstractCatWand(){
		setMaxStackSize(1);
	}

    //Available Modes
    Integer HOTBAR = 0;
    Integer INTERNAL = 1;
    
    
    //OnItemUse also triggers onItemRightClick, so inventory opens when setting corners.
    //Switched to open inventory using hotkey
    

    public void openInv(World world, EntityPlayer player)
    {
       if (!world.isRemote) {


         
        		player.openGui(Texturizer.instance, 3, world, (int) player.posX, (int) player.posY, (int)player.posZ);
       
        }
    }
    

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }



    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new AbstractCatWand.InvProvider();

    }

    private static class InvProvider implements ICapabilitySerializable<NBTBase> {

		private final IItemHandler inv = new ItemStackHandler(68) {
		};

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		}

		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
			else return null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inv, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inv, null, nbt);
		}
	}


    public boolean playerInvRemoveItem(EntityPlayer player, Tuple<Item,Integer>  item){
        //check non hotbar slots first
        for(int i = 9; i < player.inventory.getSizeInventory() ; i++) {

            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if(itemstack.getItem() == item.getFirst() && itemstack.getItem().getDamage(itemstack) == item.getSecond()){
                if(itemstack.getCount() > 0){
                    itemstack.shrink(1);
                    return true;
                }
            }
        }
        //check toolbar slots, leave 1 left in stack
        for(int i = 0; i <= 8 ; i++) {

            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if(itemstack.getItem() == item.getFirst() && itemstack.getItem().getDamage(itemstack) == item.getSecond()){
                if(itemstack.getCount() > 1){
                    itemstack.shrink(1);
                    return true;
                }
            }

        }
        return false;
    }

    public boolean wandInvRemoveItem(ItemStack wand, Tuple<Item,Integer> item){

		IItemHandler wandInv = wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);


        //check non hotbar slots first
        int FIRST_NON_PALLET_SLOT = 0;
        for(int i = FIRST_NON_PALLET_SLOT; i < wandInv.getSlots() ; i++) {

            ItemStack itemstack = wandInv.getStackInSlot(i);
            if(itemstack.getItem() == item.getFirst() && itemstack.getItem().getDamage(itemstack) == item.getSecond()){
                if(itemstack.getCount() > 0){
                    //wandInv.extractItem(i,1,true);
                    itemstack.shrink(1);

                    return true;

                }
            }
        }

        return false;
    }

    public void toggleMode(EntityPlayer player, ItemStack stack){
    	NBTTagCompound nbt =  stack.getOrCreateSubCompound("myNBT");
        Integer mode = nbt.getInteger("mode");
        
        if (mode == INTERNAL) {
    
            nbt.setInteger("mode", HOTBAR);
            player.sendMessage( new TextComponentString("Taking pallet from Hotbar"));

        }
        if (mode == HOTBAR) {
            nbt.setInteger("mode", INTERNAL);
            player.sendMessage(new TextComponentString("Taking pallet from Internal Inventory"));
        }

    }
  
   
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
		// TODO Auto-generated method stub
		
	}


}
