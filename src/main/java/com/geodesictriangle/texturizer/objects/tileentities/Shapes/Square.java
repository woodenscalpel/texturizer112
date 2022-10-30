package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public class Square extends AbstractShape {
    List<Integer> values = new ArrayList<>();
    public static int RADIUS_INDEX = 0;



    public Square(GuideTile tile){
        super(tile);
        int RADIUS = 3;
        values.add(RADIUS);
    }

    public void setValue(int id,int val){
        values.set(id,val);
    }

    public Integer getValue(int id){
        return values.get(id);
    }

    @Override
    public List<BlockPos> getBlocks() {
        List<BlockPos> blockList = new ArrayList<>();

        int RADIUS = getValue(0);

        //Texturizer.LOGGER.info("Pos that crashed {}",tile.getPos());

        //BlockPos originPos = tile.getPos(); //placeholder

        //Algorithm from (-r,-r) to (r,r) if x ==r or z ==r its on the outline so therefore in the square
        for(int x= -RADIUS; x<= RADIUS; x++){
            //blockList.add(new BlockPos(x+originPos.getX(),0+originPos.getY(),RADIUS+originPos.getZ()));
           // blockList.add(new BlockPos(x+originPos.getX(),0+originPos.getY(),-RADIUS+originPos.getZ()));
            blockList.add(new BlockPos(x,0,RADIUS));
             blockList.add(new BlockPos(x,0,-RADIUS));

        }
        for(int z= -RADIUS; z<= RADIUS; z++){
            //blockList.add(new BlockPos(RADIUS+originPos.getX(),0+originPos.getY(),z+originPos.getZ()));
            //blockList.add(new BlockPos(-RADIUS+originPos.getX(),0+originPos.getY(),z+originPos.getZ()));
            blockList.add(new BlockPos(RADIUS,0,z));
            blockList.add(new BlockPos(-RADIUS,0,z));

        }


        return moveOrigin(blockList);


    }
}
