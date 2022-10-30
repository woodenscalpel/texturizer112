package com.geodesictriangle.texturizer.objects.tileentities.Shapes;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;

public class SemiCircleArch extends AbstractShape {


    List<Integer> values = new ArrayList<>();
    public static int WIDTH_INDEX = 0;
    public static int HEIGHT_INDEX = 1;

    GuideTile guideTile;

    public SemiCircleArch(GuideTile tile){
        super(tile);
        int defaultWidthRadius = 5;
        int defaultHeight = 10;
        values.add(defaultWidthRadius);
        values.add(defaultHeight);
        guideTile = tile;
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
        BlockPos tilePos = guideTile.getPos();
        EnumFacing facing =  Objects.requireNonNull(guideTile.getWorld()).getBlockState(tilePos).getValue(FACING);


        List<BlockPos> blockList = new ArrayList<>();


        int h = getValue(1);
        int w = getValue(0);

        //BlockPos origin = new BlockPos(0,0,0);
       // BlockPos circleOrigin = new BlockPos(0,h-w,0);


//Semicircle part
        List<Tuple<Integer,Integer>> tupleList = new BresenhamCircle(w).getCircle();




        for(Tuple<Integer,Integer> tuple : tupleList){
            if(tuple.getSecond() >= 0) {
                BlockPos pos = TupleToBlockPos.tupleToBlockPos(tuple, facing);
                BlockPos yoffset = new BlockPos(0,h-w,0);
                blockList.add(pos.add(yoffset.getX(),yoffset.getY(),yoffset.getZ()));
                //blockList.add(new BlockPos((int) tuple.getA(), (int) tuple.getB() + h - w, 0));

            }
        }
        //Sides

        for(int y=0;y<(h-w);y++){
            blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(w,y),facing));
            blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(-w,y),facing));
            //blockList.add(new BlockPos(w,y,0));
           // blockList.add(new BlockPos(-w,y,0));

        }

        return moveOrigin(blockList);

    }
}
