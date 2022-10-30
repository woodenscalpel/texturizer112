package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;

public class OriginOffset {
    public BlockPos originOffset;
    public BlockPos uoriginOffset;

    public EnumFacing facing;
    public GuideTile guideTile;
    public OriginOffset(GuideTile tile){
        originOffset = new BlockPos(0,0,0);
        uoriginOffset = new BlockPos(0,0,0);

        guideTile = tile;

    }

    public void moveRelX(int amount){
        facing = Objects.requireNonNull(guideTile.getWorld()).getBlockState(guideTile.getPos()).getValue(FACING);
        if(Texturizer.chiselsandbitsloaded && guideTile.BIT_SIZE != 16) {
            switch (facing){
            case NORTH:
            default:
                umoveAbsX(-amount);
                break;
            case EAST:
                umoveAbsZ(-amount);
                break;
            case SOUTH:
                umoveAbsX(amount);
                break;
            case WEST:
                umoveAbsZ(amount);
                break;
        }
        }else {
        switch (facing){
            case NORTH:
            default:
                moveAbsX(-amount);
                break;
            case EAST:
                moveAbsZ(-amount);
                break;
            case SOUTH:
                moveAbsX(amount);
                break;
            case WEST:
                moveAbsZ(amount);
                break;
        }
        }

    }
    public void moveRelY(int amount){
        if(Texturizer.chiselsandbitsloaded && guideTile.BIT_SIZE != 16) {
            umoveAbsY(amount);
        }else{
        moveAbsY(amount);
        }
    }
    public void moveRelZ(int amount){
        facing = Objects.requireNonNull(guideTile.getWorld()).getBlockState(guideTile.getPos()).getValue(FACING);
        if(Texturizer.chiselsandbitsloaded && guideTile.BIT_SIZE != 16) {
            switch (facing){
            case NORTH:
            default:
                umoveAbsZ(amount);
                break;
            case EAST:
                umoveAbsX(-amount);
                break;
            case SOUTH:
                umoveAbsZ(-amount);
                break;
            case WEST:
               umoveAbsX(amount);
                break;
        }
        }
        else {
        switch (facing){
            case NORTH:
            default:
                moveAbsZ(amount);
                break;
            case EAST:
                moveAbsX(-amount);
                break;
            case SOUTH:
                moveAbsZ(-amount);
                break;
            case WEST:
                moveAbsX(amount);
                break;
        }
        }
    }

    private void moveAbsX(int amount){
        originOffset = originOffset.add(amount,0,0);
    }
    private void moveAbsY(int amount){
        originOffset = originOffset.add(0,amount,0);
    }
    private void moveAbsZ(int amount){
        originOffset = originOffset.add(0,0,amount);
    }
    
    private void umoveAbsX(int amount){
        uoriginOffset = uoriginOffset.add(amount,0,0);
    }
    private void umoveAbsY(int amount){
        uoriginOffset = uoriginOffset.add(0,amount,0);
    }
    private void umoveAbsZ(int amount){
        uoriginOffset = uoriginOffset.add(0,0,amount);
    }
}
