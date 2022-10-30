package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

public class TupleToBlockPos {
    public static BlockPos tupleToBlockPos(Tuple<Integer,Integer> tuple, EnumFacing FACING){
        switch(FACING){
            case EAST:
            case WEST:
                return new BlockPos(0,tuple.getSecond(), tuple.getFirst());
            case NORTH:
            case SOUTH:
            default:
                return new BlockPos(tuple.getFirst(),tuple.getSecond(),0);
        }
    }
}
