package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import net.minecraft.util.ResourceLocation;



public class OriginButtonModule extends AbstractButtonModule implements IModuleTexture {
    //TEXTURE DIRECTION KEY

    int direction;
    FACING facing;
    int mode;
    int linkedIntID;
    GuideTile tile;
    final static int originUP = 0;
    final int originDOWN = 1;
    final int originLEFT = 2;
    final int originRIGHT = 3;
    final int originFORWARD = 4;
    final int originBACK = 5;

    public static final ResourceLocation ARROW_TEXTURE_UP = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrow.png");
    public static final ResourceLocation ARROW_TEXTURE_DOWN = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowdown.png");
    public static final ResourceLocation ARROW_TEXTURE_LEFT = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowleft.png");
    public static final ResourceLocation ARROW_TEXTURE_RIGHT = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowright.png");




    public OriginButtonModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID,int facing) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
        this.direction = facing;
        this.mode = mode;
        this.linkedIntID = linkedIntID;
        this.tile=parentTile;
        switch (direction){
            case originUP:
            default:
                this.facing = FACING.UP;
                break;
            case originDOWN:
                this.facing = FACING.DOWN;
                break;
            case originLEFT:
                this.facing = FACING.LEFT;
                break;
            case originRIGHT:
                this.facing = FACING.RIGHT;
                break;
            case originFORWARD:
                this.facing = FACING.UP;
                break;
            case originBACK:
                this.facing = FACING.DOWN;
                break;
        }
    }



    @Override
    public ResourceLocation getTextureLocation(){
        switch (direction){
            case originUP:
            case originFORWARD:
            default:
            return ARROW_TEXTURE_UP;
            case originDOWN:
            case originBACK:
                return ARROW_TEXTURE_DOWN;
            case originLEFT:
                return ARROW_TEXTURE_LEFT;
            case originRIGHT:
                return ARROW_TEXTURE_RIGHT;

        }
    }


    @Override
    public FACING getTextureFacing() {
        return FACING.UP;
    }

    @Override
    public boolean isClicked(float clickX, float clickY) {
        return (x < clickX*16 && clickX*16 < x+sizex && y<clickY*16 && clickY*16 < y+sizey);
    }

    @Override
    public void onClick() {
        int amount = 1;
        switch (direction){
            case originUP:
            default:
                tile.originOffset.moveRelY(amount);
                break;
            case originDOWN:
                tile.originOffset.moveRelY(-amount);
                break;
            case originLEFT:
                tile.originOffset.moveRelX(-amount);
                break;
            case originRIGHT:
                tile.originOffset.moveRelX(amount);
                break;
            case originFORWARD:
                tile.originOffset.moveRelZ(amount);
                break;
            case originBACK:
                tile.originOffset.moveRelZ(-amount);
                break;
        }
        super.onClick();

    }

}
