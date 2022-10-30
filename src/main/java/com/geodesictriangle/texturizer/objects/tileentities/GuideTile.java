package com.geodesictriangle.texturizer.objects.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.ChiselAndBitsIntegration.ChiselBitsAddon;
import com.geodesictriangle.texturizer.objects.blocks.GuideBlock;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.AbstractModule;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.ModuleList;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.AbstractShape;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.Circle;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.DepressedArch;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.GothicArch;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.InflectedArch;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.OriginOffset;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.PointedTrefoilArch;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.SemiCircleArch;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.Square;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.ThreefoilArch;

import mod.chiselsandbits.api.APIExceptions.CannotBeChiseled;
import mod.chiselsandbits.api.APIExceptions.InvalidBitItem;
import mod.chiselsandbits.api.APIExceptions.SpaceOccupied;
import mod.chiselsandbits.api.IBitAccess;
import mod.chiselsandbits.api.IBitBrush;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GuideTile extends TileEntity implements ITickable {


    //Flag for the builder to place blocks
    public boolean BUILDING = false;



    ModuleList moduleList = new ModuleList(this);
    public List<AbstractModule> modules;

    //What screen to draw TODO save as nbt so it is persistent
    public Integer ACTIVE_SCREEN = 0;
    public Integer BIT_SIZE = 16;
    private int ACTIVE_SCREEN_MAX;
    private int ACTIVE_SCREEN_MIN = 0;

    //All the different Shapes that can be drawn
    public List<AbstractShape> shapes = new ArrayList<>();

    public int ALWAYS_ACTIVE = -1;

    public Square square = new Square(this);
    public Circle circle = new Circle(this);
    public SemiCircleArch semiarch = new SemiCircleArch(this);
    public GothicArch gothicArch = new GothicArch(this);
    public DepressedArch depressedArch = new DepressedArch(this);
    public InflectedArch inflectedArch = new InflectedArch(this);
    public ThreefoilArch threefoilArch = new ThreefoilArch(this);
    public PointedTrefoilArch pointedTrefoilArch = new PointedTrefoilArch(this);
    public OriginOffset originOffset = new OriginOffset(this);






/*
    public GuideTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

    }
*/

    public GuideTile(){
    	/*
        this(ModTileEntityTypes.GUIDE_TILE.get());
        */
        modules = moduleList.getAllModules();

        //ACTIVE_SCREEN = 0;

        //populate shapes list
        shapes.add(square);
        shapes.add(circle);
        shapes.add(semiarch);
        shapes.add(gothicArch);
        shapes.add(depressedArch);
        shapes.add(inflectedArch);
        shapes.add(threefoilArch);
        shapes.add(pointedTrefoilArch);




        ACTIVE_SCREEN_MAX = shapes.size()-1;
    }



    public void changeVar(int varID, int action){
        AbstractShape activeshape = this.shapes.get(ACTIVE_SCREEN);

            if (varID == -1) {
                if (action == ModuleList.INC_MODE) {
                    ACTIVE_SCREEN++;
                    if(ACTIVE_SCREEN > ACTIVE_SCREEN_MAX){ACTIVE_SCREEN = ACTIVE_SCREEN_MIN;}
                }
                if (action == ModuleList.DEC_MODE) {
                    ACTIVE_SCREEN--;
                    if(ACTIVE_SCREEN < ACTIVE_SCREEN_MIN){ACTIVE_SCREEN = ACTIVE_SCREEN_MAX;}

                }
            }
            else if (varID == -2) {
                if (action == ModuleList.INC_MODE) {
                    BIT_SIZE++;
                    if(BIT_SIZE>15){this.originOffset.uoriginOffset = new BlockPos(0,0,0);} //go back to normal block placing TODO this whole microblock offset system is the most disgusting thing ive seen in my life
                    if(BIT_SIZE>16){BIT_SIZE=16;}
                }
                if (action == ModuleList.DEC_MODE) {
                    BIT_SIZE--;
                    if(BIT_SIZE < 1){BIT_SIZE=1;}

                }
            }
            else {

            if (action == ModuleList.INC_MODE) {
                activeshape.setValue(varID, activeshape.getValue(varID) + 1);
            }

            if (action == ModuleList.DEC_MODE) {
                activeshape.setValue(varID, activeshape.getValue(varID) - 1);
            }
            if (action == ModuleList.INC2_MODE) {
                    activeshape.setValue(varID, activeshape.getValue(varID) + 2);
                }
            if (action == ModuleList.DEC2_MODE) {
                    activeshape.setValue(varID, activeshape.getValue(varID) - 2);
                }
        }

    }





    /**
     * Don't render the object if the player is too far away
     * @return the maximum distance squared at which the TER should render
     */
    @Override
    public double getMaxRenderDistanceSquared()
    {
        final int MAXIMUM_DISTANCE_IN_BLOCKS = 1024;
        return MAXIMUM_DISTANCE_IN_BLOCKS * MAXIMUM_DISTANCE_IN_BLOCKS;
    }

    /** Return an appropriate bounding box enclosing the TER
     * This method is used to control whether the TER should be rendered or not, depending on where the player is looking.
     * The default is the AABB for the parent block, which might be too small if the TER renders outside the borders of the
     *   parent block.
     * If you get the boundary too small, the TER may disappear when you aren't looking directly at it.
     * @return an appropriately size AABB for the TileEntity
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        // if your render should always be performed regardless of where the player is looking, use infinite
        //   Your should also change TileEntitySpecialRenderer.isGlobalRenderer().



        return INFINITE_EXTENT_AABB;
    }
    Iterator<BlockPos> blockIterator;
    public void build() {
        BUILDING = true;
        AbstractShape activeshape = this.shapes.get(ACTIVE_SCREEN);
        List<BlockPos> blockList = activeshape.getBlocks();
        blockIterator = blockList.iterator();
    }

    public Tuple<Block,Integer> getAndRemoveInvBlock(){
        BlockPos tilePos = this.getPos();
        EnumFacing facing =  Objects.requireNonNull(this.getWorld()).getBlockState(tilePos).getValue(GuideBlock.FACING);
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
            TileEntity potentialContainer = this.getWorld().getTileEntity(invPos);
            if(potentialContainer != null) {
            IItemHandler inv = potentialContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
            if(inv != null){
                for(int slot  = 0; slot<inv.getSlots();slot++){
                    if(inv.getStackInSlot(slot).getItem() instanceof ItemBlock){
                        Texturizer.logger.info("block {} found in slot {}",inv.getStackInSlot(slot),slot);
                        Block block = ((ItemBlock) inv.getStackInSlot(slot).getItem()).getBlock();
                        inv.getStackInSlot(slot).shrink(1);
                        return new Tuple<Block,Integer>(block,inv.getStackInSlot(slot).getItemDamage());

                    }

                }
            }
            }

        //}
        return new Tuple<Block,Integer>(Blocks.AIR,0);
    }

    @Override
    public void update() {

        if(BUILDING){

            if (!world.isRemote) {
                AbstractShape activeshape = this.shapes.get(ACTIVE_SCREEN);
                List<BlockPos> blockList = activeshape.getBlocks();
                
                
                
               if(blockIterator.hasNext()){
               // for (BlockPos pos : blockList) {
                    BlockPos tilePos = this.getPos();
                    BlockPos nextPos = blockIterator.next();
                    if(BIT_SIZE==16 || !Texturizer.chiselsandbitsloaded) {

                    BlockPos abspos = tilePos.add(nextPos.getX(),nextPos.getY(),nextPos.getZ());
                    if (world.getBlockState(abspos).getBlock() == Blocks.AIR) {
                    	Tuple<Block,Integer> block = getAndRemoveInvBlock();
                        if (block.getFirst() != Blocks.AIR) {
                            world.setBlockState(abspos, block.getFirst().getStateFromMeta(block.getSecond()));
                        } else {
                            BUILDING = false;
                        }
                    }
                    }else{
                    	
                    	ChiselBitsAddon addon = new ChiselBitsAddon();
                    	addon.placeBits(this,world,nextPos);
        
                    }

                }
                    
               else {
                BUILDING = false;
               }
       
                
                
                
                
            }

        }
    }
//todo Serialize data and store in nbt

}



    
