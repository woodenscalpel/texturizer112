package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;

public class GothicArch extends AbstractShape {


    List<Integer> values = new ArrayList<>();
    public static int RADIUS_INDEX = 0;

    GuideTile guideTile;

    public GothicArch(GuideTile tile){
        super(tile);
        int defaultRadius = 5;
        values.add(defaultRadius);

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


        int radius = getValue(0);



//Semicircle part
        List<Tuple<Integer,Integer>> tupleList = new BresenhamCircle(radius).getCircle();




        for(Tuple<Integer,Integer> tuple : tupleList){
            if(tuple.getSecond() >= 0) {
                BlockPos pos = TupleToBlockPos.tupleToBlockPos(tuple, facing);
                BlockPos offset1 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(radius/2,0),facing);
                BlockPos offset2 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(-radius/2,0),facing);

                if((tuple.getFirst()+radius/2)<0){
                    blockList.add(pos.add(offset1.getX(), offset1.getY(), offset1.getZ()));
                }
                if((tuple.getFirst()-radius/2)>=0) {
                    blockList.add(pos.add(offset2.getX(), offset2.getY(), offset2.getZ()));
                }


            }
        }
        //Sides



        return moveOrigin(blockList);

    }
}