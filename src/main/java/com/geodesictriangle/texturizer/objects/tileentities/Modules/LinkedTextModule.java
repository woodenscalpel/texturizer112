package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.AbstractShape;

public class LinkedTextModule extends AbstractTextModule {
 String text = "notnull";
 GuideTile tile;


    public LinkedTextModule(GuideTile parentTile, float x, float y, float sizex, float sizey, int activeScreenID, int linkedIntID) {
        super(parentTile,x, y, sizex, sizey, activeScreenID);
        this.linkedVarID = linkedIntID;
        this.tile = parentTile;
    }

    public void updateText(){
        if(linkedVarID ==-1){

            switch(tile.ACTIVE_SCREEN){
                case 0:
                    text = "SQUARE";
                    break;
                case 1:
                    text = "CIRCLE";
                    break;
                case 2:
                    text = "SEMICIRCLE ARCH";
                    break;
                case 3:
                    text = "GOTHIC ARCH";
                    break;
                case 4:
                    text = "DEPRESSED ARCH";
                    break;
                case 5:
                    text = "INFLECTED ARCH";
                    break;
                case 6:
                    text = "TREFOIL ARCH";
                    break;
                case 7:
                    text = "POINTED TREFOIL ARCH";
                    break;
                default:
                    text = "INVALID SHAPE";
            }
        }else if(linkedVarID == -2) {
        	text = tile.BIT_SIZE.toString();
        }
        else {
            AbstractShape activeshape = tile.shapes.get(tile.ACTIVE_SCREEN);
            text = activeshape.getValue(linkedVarID).toString();
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
