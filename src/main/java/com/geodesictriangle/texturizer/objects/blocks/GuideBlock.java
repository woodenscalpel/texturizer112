package com.geodesictriangle.texturizer.objects.blocks;



import java.util.List;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.init.BlockInit;
import com.geodesictriangle.texturizer.init.ItemInit;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.AbstractModule;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.IModuleClickable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GuideBlock extends BlockContainer
{
	public static final AxisAlignedBB GUIDE_BB_S = new AxisAlignedBB(0D, 0.0D, 0D, 1D, 1.0D, 0.0625D);
	public static final AxisAlignedBB GUIDE_BB_N = new AxisAlignedBB(0D, 0.0D,15.0/16.0D, 1D, 1.0D, 1D);

	public static final AxisAlignedBB GUIDE_BB_E = new AxisAlignedBB(0, 0.0D, 0D, 0.0625D, 1.0D, 1D);
	public static final AxisAlignedBB GUIDE_BB_W = new AxisAlignedBB(15.0/16.0D, 0.0D, 0D, 1D, 1.0D, 1D);

	
	//Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
	    this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); 
	}
	
	public GuideBlock(String name, Material material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Texturizer.TEXTURIZERTAB);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	
	//Facing
	@Override
	public IBlockState getStateFromMeta(int meta) 
    {
		EnumFacing facing = EnumFacing.getFront(meta);

		if(facing.getAxis()==EnumFacing.Axis.Y) 
		{
			facing=EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING, facing);
    }
	
	//Facing
	@Override
	public int getMetaFromState(IBlockState state) 
    {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
    }
	    
	//Facing
    @Override
	protected BlockStateContainer createBlockState() 
    {
    	return new BlockStateContainer(this, new IProperty[]{FACING});
    }
    
    //Facing
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos,EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) 
    {
	  return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	//Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		switch(((EnumFacing)state.getValue(FACING)))
        {
            case SOUTH:
            default:
                return GUIDE_BB_S;
            case NORTH:
                return GUIDE_BB_N;
            case EAST:
                return GUIDE_BB_E;
            case WEST:
                return GUIDE_BB_W;
        }
	}

    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
    { 
    	/*
         if (!worldIn.isRemote()) {
       ServerWorld serverWorld = (ServerWorld) world;

        GuideTile tileEntity = (GuideTile) serverWorld.getTileEntity(pos);

       }
       */

    	
    	
    	
         GuideTile tileEntity = (GuideTile) world.getTileEntity(pos);
        assert tileEntity != null : "NULL TILE ENTITY";
        List<AbstractModule> modules = tileEntity.modules;//moduleList.getAllModules();


                for (AbstractModule module : modules) {
                    if (module.activeScreenID == tileEntity.ACTIVE_SCREEN || module.activeScreenID == -1) {
                        if (module instanceof IModuleClickable) {
                            ClickModuleFacing(module, hitX,hitY,hitZ, pos, (EnumFacing)state.getValue(FACING));
                        }
                    }
                }

        return true;
    }

//Changes blockhit coords to local screen coords

    private void ClickModuleFacing(AbstractModule moduleClickable,float hitX, float hitY, float hitZ,BlockPos pos, EnumFacing facing){
        IModuleClickable module = (IModuleClickable) moduleClickable;

        float x = hitX;
        float y = hitY;
        float z = hitZ;
        float relX;
        switch (facing) {
            case NORTH:
                relX = 1-x;
                break;
            case EAST:
                relX = 1-z;
                break;
            case WEST:
                relX = z;
                break;
            case SOUTH:
            default:
                relX = x;
                break;
        }
        if(module.isClicked(relX,y)){
            module.onClick();
        }
    }


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new GuideTile();
	}
}


