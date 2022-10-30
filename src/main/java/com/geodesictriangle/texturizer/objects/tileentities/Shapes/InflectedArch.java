package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;
import static java.lang.StrictMath.sqrt;

public class InflectedArch extends AbstractShape {


        List<Integer> values = new ArrayList<>();
        public static int WIDTH_INDEX = 0;
        public static int HEIGHT_INDEX = 1;

        GuideTile guideTile;

        public InflectedArch(GuideTile tile){
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
            int r = getValue(0);
            int inflectionx = r / 2;


            //BlockPos origin = new BlockPos(0,0,0);
           // BlockPos circleOrigin = new BlockPos(0,h-r,0);


//Semicircle part
            List<Tuple<Integer,Integer>> tupleList = new BresenhamCircle(r).getCircle();




            for(Tuple<Integer,Integer> tuple : tupleList){
                BlockPos pos = TupleToBlockPos.tupleToBlockPos(tuple, facing);
                if(tuple.getSecond() >= 0 && (tuple.getFirst() < -inflectionx || tuple.getFirst() > inflectionx) ) {

                    //CENTER CIRCLE
                   // BlockPos pos = TupleToBlockPos.tupleToBlockPos(tuple, facing);
                    BlockPos yoffset = new BlockPos(0,h-r,0);
                    blockList.add(pos.add(yoffset.getX(),yoffset.getY(),yoffset.getZ()));
                    //CORNER CIRCLES






                } else {


                    double auxCenterH = h - r + sqrt(3) * r;
                    BlockPos offset1 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(r, (int) (auxCenterH)), facing);
                    BlockPos offset2 = TupleToBlockPos.tupleToBlockPos(new Tuple<>(-r, (int) (auxCenterH)), facing);
                    if (( tuple.getFirst() + r) < inflectionx) {

                        blockList.add(pos.add(offset1.getX(), offset1.getY(), offset1.getZ()));
                    }
                    if (( tuple.getFirst() - r) > -inflectionx) {
                        blockList.add(pos.add(offset2.getX(), offset2.getY(), offset2.getZ()));
                    }

                }


            }
            //Sides

            for(int y=0;y<(h-r);y++){
                blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(r,y),facing));
                blockList.add(TupleToBlockPos.tupleToBlockPos(new Tuple<>(-r,y),facing));


            }

            return moveOrigin(blockList);

        }
    }