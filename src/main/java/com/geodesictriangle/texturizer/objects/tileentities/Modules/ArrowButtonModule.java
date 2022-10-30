package com.geodesictriangle.texturizer.objects.tileentities.Modules;


import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;


import net.minecraft.util.ResourceLocation;




public class ArrowButtonModule extends AbstractButtonModule implements IModuleTexture {


    FACING facing;
    int mode;
    int linkedIntID;
    GuideTile tile;

    public static final ResourceLocation ARROW_TEXTURE_UP = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrow.png");
    public static final ResourceLocation ARROW_TEXTURE_DOWN = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowdown.png");
    public static final ResourceLocation ARROW_TEXTURE_LEFT = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowleft.png");
    public static final ResourceLocation ARROW_TEXTURE_RIGHT = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/arrowright.png");

    //TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();


/*
    TextureAtlasSprite spriteup = texturemap.getAtlasSprite(ARROW_TEXTURE_UP.toString());
    TextureAtlasSprite spritedown = texturemap.getAtlasSprite(ARROW_TEXTURE_DOWN.toString());
    TextureAtlasSprite spriteleft = texturemap.getAtlasSprite(ARROW_TEXTURE_LEFT.toString());
    TextureAtlasSprite spriteright = texturemap.getAtlasSprite(ARROW_TEXTURE_RIGHT.toString());

*/

    public ArrowButtonModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID,int mode, FACING facing,int linkedIntID) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
        this.facing = facing;
        this.mode = mode;
        this.linkedIntID = linkedIntID;
        this.tile=parentTile;
    }



    @Override
    public ResourceLocation getTextureLocation(){
        switch (facing) {
            case UP:
            default:
                return ARROW_TEXTURE_UP;
            case DOWN:
                return ARROW_TEXTURE_DOWN;
            case LEFT:
                return ARROW_TEXTURE_LEFT;
            case RIGHT:
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
        tile.changeVar(linkedIntID,mode);
        super.onClick();

    }
}
