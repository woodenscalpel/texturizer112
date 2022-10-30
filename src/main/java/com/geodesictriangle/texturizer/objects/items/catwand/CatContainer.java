package com.geodesictriangle.texturizer.objects.items.catwand;

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
public class CatContainer extends Container {
    //@ObjectHolder("texturizer:texturewand" ) public static ContainerType<ContainerWithPallet> TYPE;

/*
    public static WandContainer fromNetwork(int windowId, InventoryPlayer inv, PacketBuffer buf) {
        //super(TEXTURE_WAND.get(), windowId);
        Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        return new WandContainer(windowId, inv, inv.player.getHeldItem(hand));

    }
   */
    public final ItemStack wand;


	public CatContainer(InventoryPlayer playerInv, ItemStack wandInventory, EntityPlayer player) {

       // super(TYPE, windowId);
        this.wand = wandInventory;
        IItemHandler wandInv =  wand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        int slotsize = 18;
        //Wand Bulk Inventory
        int startX = 152;
        int startY = 31;
        int nRows = 4;
        int nCols = 5;

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


}
