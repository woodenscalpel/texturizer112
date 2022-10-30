package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public abstract class AbstractButtonModule extends AbstractModule implements IModuleClickable{
    public AbstractButtonModule(GuideTile parent,float x, float y, float sizex, float sizey, int activeScreenID) {
        super(parent,x, y, sizex, sizey, activeScreenID);

    }


    @Override
    public void onClick() {
       // tile.resetText();
    }
}
