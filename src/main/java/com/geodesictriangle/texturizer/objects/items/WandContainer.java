package com.geodesictriangle.texturizer.objects.items;

import javax.annotation.Nonnull;

import com.geodesictriangle.texturizer.Texturizer;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

@MethodsReturnNonnullByDefault
public class WandContainer extends Container {
    //@ObjectHolder("texturizer:texturewand" ) public static ContainerType<ContainerWithPallet> TYPE;

/*
    public static WandContainer fromNetwork(int windowId, InventoryPlayer inv, PacketBuffer buf) {
        //super(TEXTURE_WAND.get(), windowId);
        Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        return new WandContainer(windowId, inv, inv.player.getHeldItem(hand));

    }
   */
    public final ItemStack wand;


	public WandContainer(InventoryPlayer playerInv, ItemStack wandInventory, EntityPlayer player) {

       // super(TYPE, windowId);
        this.wand = wandInventory;
        IItemHandler wandInv =  wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        int slotsize = 18;
        //Wand Pallet Inventory
        int startX = 8;
        int startY = 31;
        int nRows = 4;
        int nCols = 4;

        for(int row = 0; row <nRows; ++row){
            for(int col = 0; col < nCols; ++col){
                //this.addSlot(new SlotItemHandler(wandInv,(row*nCols)+col, startX+col*slotsize, startY+row*slotsize));
                this.addSlotToContainer(new FakeSlotItemHandler(wandInv,(row*nCols)+col, startX+col*slotsize, startY+row*slotsize));


            }
        }
        //Wand Bulk Inventory
        startX = 98;
        startY = 31;
        nRows = 4;
        nCols = 8;

        for(int row = 0; row <nRows; ++row){
            for(int col = 0; col < nCols; ++col){
                this.addSlotToContainer(new SlotItemHandler(wandInv,16+(row*nCols)+col, startX+col*slotsize, startY+row*slotsize));

            }
        }

        //Player Inventory
        int startPlayerInvX = 44;
        int startPlayerInvY = 115;


        for(int row = 0; row <3; ++row){
            for(int col = 0; col < 9; ++col){
                this.addSlotToContainer(new Slot(playerInv,9+(row*9)+col, startPlayerInvX+col*slotsize, startPlayerInvY+row*slotsize));

            }
        }

        //Hotbar
        int hotbarY = startPlayerInvY+58;
        for(int col = 0; col < 9; ++col){
            this.addSlotToContainer(new Slot(playerInv,col, startPlayerInvX+col*slotsize, hotbarY));

        }

    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        ItemStack main = player.getHeldItemMainhand();
        ItemStack off = player.getHeldItemOffhand();
        return !main.isEmpty() && main == wand || !off.isEmpty() && off == wand;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 36) {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }


    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if(slotId >= 0 && slotId <= 15){
            this.inventorySlots.get(slotId).putStack(player.inventory.getItemStack());
        }
        return super.slotClick(slotId,dragType,clickTypeIn,player);
    }



}
