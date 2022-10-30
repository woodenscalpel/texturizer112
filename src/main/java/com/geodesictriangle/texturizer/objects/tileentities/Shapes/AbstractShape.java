package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public abstract class AbstractShape {
    GuideTile tile;
    AbstractShape(GuideTile tile){
        this.tile=tile;
    }
    public abstract void setValue(int id, int val);
    public abstract Integer getValue(int id);
    public abstract List<BlockPos> getBlocks();

    List<BlockPos> moveOrigin(List<BlockPos> list){
        List<BlockPos> newList = new ArrayList<>();
        for(BlockPos pos : list){
            newList.add(pos.add(tile.originOffset.originOffset));

        }
        return newList;
    }
}
