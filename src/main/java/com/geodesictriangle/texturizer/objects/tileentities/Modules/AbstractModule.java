package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public abstract class AbstractModule {
    public float x,y,sizex,sizey;
    public int activeScreenID;
    public int linkedVarID;
    GuideTile tile;
    public AbstractModule(GuideTile parentTile,float x, float y, float sizex, float sizey,int activeScreenID){
        this.x = x;
        this.y = y;
        this.sizex = sizex;
        this.sizey = sizey;

        this.activeScreenID = activeScreenID;
        this.tile = parentTile;
    }

    void linkVar(int id){
        linkedVarID = id;
    }


}
