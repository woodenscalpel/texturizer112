package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.GuideTile;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.*;

import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;



public class ModuleList {
     //List<AbstractModule> moduleList;
    List<AbstractModule> moduleList = new ArrayList<AbstractModule>()

    {
     };

    //SHAPE IDS
    int ALWAYS_ACTIVE = -1;
    int SQUARE_ID = 0;
    int CIRCLE_ID = 1;
    int SEMI_ARCH_ID = 2;
    int GOTHIC_ARCH_ID = 3;
    int DEPRESSED_ARCH_ID = 4;
    int INFL_ARCH_ID = 5;
    int THREEFOIL_ARCH_ID = 6;
    int POINTED_THREEFOIL_ARCH_ID = 7;





    //INC/DEC Mode for modules
    public static int INC_MODE = 0;
    public static int DEC_MODE = 1;
    public static int INC2_MODE = 2;
    public static int DEC2_MODE = 3;



    public ModuleList(GuideTile tile){


        //CREATE MODULES

        //Shape Switcher
        ArrowButtonModule SCREEN_INC = new ArrowButtonModule(tile,13,13,2,2,ALWAYS_ACTIVE,INC_MODE,FACING.RIGHT,-1);
        ArrowButtonModule SCREEN_DEC = new ArrowButtonModule(tile,1,13,2,2,ALWAYS_ACTIVE,DEC_MODE,FACING.LEFT,-1);
        LinkedTextModule SCREEN_TEXT = new LinkedTextModule(tile,3.2f,14.7f,11,2,ALWAYS_ACTIVE,-1);
        


        



        //Direction key. Cant use the enum because there are 6 directions
        int originUP = 0;
        int originDOWN = 1;
        int originLEFT = 2;
        int originRIGHT = 3;
        int originFORWARD = 4;
        int originBACK = 5;

        //Origin mover
        OriginButtonModule OriginUp = new OriginButtonModule(tile,11,6,2,2,ALWAYS_ACTIVE,originUP);
        OriginButtonModule OriginDown = new OriginButtonModule(tile,11,4,2,2,ALWAYS_ACTIVE,originDOWN);
        OriginButtonModule OriginLeft = new OriginButtonModule(tile,9,5,2,2,ALWAYS_ACTIVE,originLEFT);
        OriginButtonModule OriginRight = new OriginButtonModule(tile,13,5,2,2,ALWAYS_ACTIVE,originRIGHT);
        OriginButtonModule OriginForward = new OriginButtonModule(tile,13,7,2,2,ALWAYS_ACTIVE,originFORWARD);
        OriginButtonModule OriginBack = new OriginButtonModule(tile,9,3,2,2,ALWAYS_ACTIVE,originBACK);
        LabelTextModule    originLabel = new LabelTextModule(tile,9,10,8,2,ALWAYS_ACTIVE,"Origin Position");



        //Build Button
        BuildButtonModule BUILD = new BuildButtonModule(tile,9.5f,1,5,2,ALWAYS_ACTIVE);


        //Origin Mover

        //MicroBlock Scaler
        if(Texturizer.chiselsandbitsloaded) {
        moduleList.add(new ArrowButtonModule(tile, 6, 8 + 2, 2, 2, -1, INC_MODE, FACING.UP, -2));
        moduleList.add(new ArrowButtonModule(tile, 6, 8, 2, 2, -1, DEC_MODE, FACING.DOWN, -2));
        moduleList.add(new LinkedTextModule(tile, 6 + 2.2f, 10 + 1.7f, 3, 2, -1, -2));
        moduleList.add(new LabelTextModule(tile,6,13,8,2,-1,"Bit Size"));

        }
        //Shape Pictures (Maybe)



        moduleList.add(SCREEN_INC);
        moduleList.add(SCREEN_DEC);
        moduleList.add(SCREEN_TEXT);
        moduleList.add(BUILD);
        moduleList.add(OriginUp);
        moduleList.add(OriginDown);
        moduleList.add(OriginLeft);
        moduleList.add(OriginRight);
        moduleList.add(OriginForward);
        moduleList.add(OriginBack);
        moduleList.add(originLabel);




//CIRCLE
         addCombinedModule(moduleList, tile,2, 2,CIRCLE_ID,1, Circle.RADIUS_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,CIRCLE_ID,"Radius"));

//SQUARE
        addCombinedModule(moduleList, tile,2, 2,SQUARE_ID,1, Square.RADIUS_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,SQUARE_ID,"Radius"));

//SEMIARCH
        //Width
        addCombinedModule(moduleList, tile,2, 2,SEMI_ARCH_ID,2, SemiCircleArch.WIDTH_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,SEMI_ARCH_ID,"Width"));

        //Height
        addCombinedModule(moduleList, tile,2, 8,SEMI_ARCH_ID,1,SemiCircleArch.HEIGHT_INDEX);
        moduleList.add(new LabelTextModule(tile,2,13,5,2,SEMI_ARCH_ID,"Height"));


        //GOTHICARCH
    addCombinedModule(moduleList, tile,2, 2,GOTHIC_ARCH_ID,1, GothicArch.RADIUS_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,GOTHIC_ARCH_ID,"Radius"));

        //GOTHICARCH
        addCombinedModule(moduleList, tile,2, 2,DEPRESSED_ARCH_ID,1, DepressedArch.RADIUS_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,DEPRESSED_ARCH_ID,"Radius"));

        addCombinedModule(moduleList, tile,2, 8,DEPRESSED_ARCH_ID,2,DepressedArch.OFFSET_INDEX);
        moduleList.add(new LabelTextModule(tile,2,13,5,2,DEPRESSED_ARCH_ID,"Offset"));

//INFLECTEDARCH
        //Width
        addCombinedModule(moduleList, tile,2, 2,INFL_ARCH_ID,2,InflectedArch.WIDTH_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,INFL_ARCH_ID,"Width"));

        //Height
        addCombinedModule(moduleList, tile,2, 8,INFL_ARCH_ID,1,InflectedArch.HEIGHT_INDEX);
        moduleList.add(new LabelTextModule(tile,2,13,5,2,INFL_ARCH_ID,"Height"));

        //treARCH
        //Width
        addCombinedModule(moduleList, tile,2, 2,THREEFOIL_ARCH_ID,2,ThreefoilArch.WIDTH_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,THREEFOIL_ARCH_ID,"Width"));

        //Height
        addCombinedModule(moduleList, tile,2, 8,THREEFOIL_ARCH_ID,1,ThreefoilArch.HEIGHT_INDEX);
        moduleList.add(new LabelTextModule(tile,2,13,5,2,THREEFOIL_ARCH_ID,"Height"));

        //pointtreARCH
        //Width
        addCombinedModule(moduleList, tile,2, 2,POINTED_THREEFOIL_ARCH_ID,2,PointedTrefoilArch.WIDTH_INDEX);
        moduleList.add(new LabelTextModule(tile,2,7,5,2,POINTED_THREEFOIL_ARCH_ID,"Width"));

        //Height
        addCombinedModule(moduleList, tile,2, 8,POINTED_THREEFOIL_ARCH_ID,1,PointedTrefoilArch.HEIGHT_INDEX);
        moduleList.add(new LabelTextModule(tile,2,13,5,2,POINTED_THREEFOIL_ARCH_ID,"Height"));

    }

       public  List<AbstractModule> getAllModules(){


        return moduleList;
    }

    public void addCombinedModule(List<AbstractModule>  list,GuideTile tile,int x, int y,int screenID,int incamount,int varId){

            if(incamount == 1) {
                list.add(new ArrowButtonModule(tile, x, y + 2, 2, 2, screenID, INC_MODE, FACING.UP, varId));
                list.add(new ArrowButtonModule(tile, x, y, 2, 2, screenID, DEC_MODE, FACING.DOWN, varId));
                list.add(new LinkedTextModule(tile, x + 2.2f, y + 1.7f, 3, 2, screenID, varId));
            }
            if(incamount == 2) {
                list.add(new ArrowButtonModule(tile, x, y + 2, 2, 2, screenID, INC2_MODE, FACING.UP, varId));
                list.add(new ArrowButtonModule(tile, x, y, 2, 2, screenID, DEC2_MODE, FACING.DOWN, varId));
                list.add(new LinkedTextModule(tile, x + 2.2f, y + 1.7f, 3, 2, screenID, varId));
            }

    }
}
