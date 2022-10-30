package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;

public class LabelTextModule extends AbstractTextModule {


public LabelTextModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID,String text) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
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