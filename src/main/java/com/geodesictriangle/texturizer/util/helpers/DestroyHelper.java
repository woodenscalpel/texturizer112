package com.geodesictriangle.texturizer.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DestroyHelper {
    public static boolean destroyBlockNoParticles(BlockPos pos, boolean dropBlock,World world)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (block.isAir(iblockstate, world, pos))
        {
            return false;
        }
        else
        {
            //this.playEvent(2001, pos, Block.getStateId(iblockstate));

            if (dropBlock)
            {
                block.dropBlockAsItem(world, pos, iblockstate, 0);
            }

            //return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            return world.setBlockToAir(pos);
            
       
        }
    }
}
