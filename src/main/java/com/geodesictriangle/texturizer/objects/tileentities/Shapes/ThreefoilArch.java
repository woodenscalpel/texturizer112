package com.geodesictriangle.texturizer.objects.tileentities.Shapes;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;

public class ThreefoilArch extends AbstractShape {


    List<Integer> values = new ArrayList<>();
    public static int WIDTH_INDEX = 0;
    public static int HEIGHT_INDEX = 1;

    GuideTile guideTile;

    public ThreefoilArch(GuideTile tile){
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


        int w = getValue(0)/2;
        int h = getValue(1);


       // BlockPos origin = new BlockPos(0,0,0);
        BlockPos circleOrigin = new BlockPos(0,h-w,0);
        BlockPos pos1 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(w,0), facing);
        BlockPos pos2 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(0,w), facing);
        BlockPos pos3 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(-w,0), facing);

        BlockPos circle1Origin = new BlockPos(circleOrigin.getX()+pos1.getX(),circleOrigin.getY()+pos1.getY(),circleOrigin.getZ()+pos1.getZ());
        BlockPos circle2Origin = new BlockPos(circleOrigin.getX()+pos2.getX(),circleOrigin.getY()+pos2.getY(),circleOrigin.getZ()+pos2.getZ());
        BlockPos circle3Origin = new BlockPos(circleOrigin.getX()+pos3.getX(),circleOrigin.getY()+pos3.getY(),circleOrigin.getZ()+pos3.getZ());




//Semicircle part
        List<Tuple<Integer,Integer>> tupleList = new BresenhamCircle(w).getCircle();




        for(Tuple<Integer,Integer> tuple : tupleList){
            if(tuple.getSecond() >= 0) {
                BlockPos pos = TupleToBlockPos.tupleToBlockPos(tuple, facing);
                if(tuple.getFirst() > 0) {
                    blockList.add(new BlockPos(pos.getX() + circle1Origin.getX(), pos.getY() + circle1Origin.getY(), pos.getZ() + circle1Origin.getZ()));
                }

                    blockList.add(new BlockPos(pos.getX() + circle2Origin.getX(), pos.getY() + circle2Origin.getY(), pos.getZ() + circle2Origin.getZ()));

                if(tuple.getFirst() < 0) {
                    blockList.add(new BlockPos(pos.getX() + circle3Origin.getX(), pos.getY() + circle3Origin.getY(), pos.getZ() + circle3Origin.getZ()));
                }

            //blockList.add(new BlockPos((int) tuple.getA(), (int) tuple.getB() + h - w, 0));

            }
        }
        //Sides

        for(int y=0;y<(h-w);y++){
            blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(2*w,y),facing));
            blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(-2*w,y),facing));

        }

        return moveOrigin(blockList);

    }
}