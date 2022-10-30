package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public abstract class AbstractTextModule extends AbstractModule {
    String text;
    GuideTile tile;

    public AbstractTextModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
        this.tile = parentTile;
        this.text = text;
    }

    public void updateText(){}

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
