package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

import net.minecraft.util.ResourceLocation;


public class BuildButtonModule extends AbstractButtonModule implements IModuleTexture {


    FACING facing;
    int mode;
    int linkedIntID;
    GuideTile tile;

    public static final ResourceLocation BUILD_TEXTURE = new ResourceLocation(Texturizer.MODID, "textures/buildingguide/build.png");



    public BuildButtonModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
        this.tile=parentTile;
    }



    @Override
    public ResourceLocation getTextureLocation(){
        return BUILD_TEXTURE;
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
        Texturizer.logger.info("Building");
        tile.build();
        super.onClick();


    }
}