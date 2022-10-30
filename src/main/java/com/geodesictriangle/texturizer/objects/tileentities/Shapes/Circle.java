package com.geodesictriangle.texturizer.objects.tileentities.Shapes;


import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;


public class Circle extends AbstractShape {


    List<Integer> values = new ArrayList<>();
    public static int RADIUS_INDEX = 0;


    public Circle(GuideTile tile){
        super(tile);
        int RADIUS = 3;
        values.add(RADIUS);
    }

    public void setValue(int id,int val){
        values.set(id,val);
    }

    public Integer getValue(int id){
        if(id==-1){return 0;}
        return values.get(id);
    }

    @Override
    public List<BlockPos> getBlocks() {
        List<BlockPos> blockList = new ArrayList<>();

        int RADIUS = getValue(0);

        List<Tuple<Integer,Integer>> tupleList = new BresenhamCircle(RADIUS).getCircle();


        for(Tuple<Integer,Integer> tuple : tupleList){
            blockList.add(new BlockPos( tuple.getFirst(),0, tuple.getSecond()));
        }

        return moveOrigin(blockList);
    }
}
