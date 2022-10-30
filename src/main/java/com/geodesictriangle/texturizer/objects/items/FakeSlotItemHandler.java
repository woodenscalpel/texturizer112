package com.geodesictriangle.texturizer.objects.items;

import net.minecraft.inventory.IInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FakeSlotItemHandler extends Slot
{
    private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
    private final IItemHandler itemHandler;
    private final int index;

    public FakeSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
      return false;
    }
    



    /**
     * Helper fnct to get the stack in the slot.
     */
    @Override
    @Nonnull
    public ItemStack getStack()
    {
        return this.getItemHandler().getStackInSlot(index);
    }

    // Override if your IItemHandler does not implement IItemHandlerModifiable
    /**
     * Helper method to put a stack in the slot.
     */
    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        if( !stack.isEmpty() )
        {
            stack = stack.copy();
            if( stack.getCount() > 1 )
            {
                stack.setCount( 1 );
            }
            else if( stack.getCount() < -1 )
            {
                stack.setCount( -1 );
            }
        }


        ((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
        this.onSlotChanged();
    }

    /**
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     */
    @Override
    public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
    {

    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack)
    {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);

        IItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(index);
        if (handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

            handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);

            ItemStack remainder = handlerModifiable.insertItem(index, maxAdd, true);

            handlerModifiable.setStackInSlot(index, currentStack);

            return maxInput - remainder.getCount();
        }
        else
        {
            ItemStack remainder = handler.insertItem(index, maxAdd, true);

            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount)
    {
        return ItemStack.EMPTY;
    }

    public IItemHandler getItemHandler()
    {
        return itemHandler;
    }

    @Override
    public boolean isSameInventory(Slot other)
    {
        return other instanceof SlotItemHandler && ((SlotItemHandler) other).getItemHandler() == this.itemHandler;
    }
}