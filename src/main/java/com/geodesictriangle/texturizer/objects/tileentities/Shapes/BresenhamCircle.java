package com.geodesictriangle.texturizer.objects.tileentities.Shapes;

import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

/*
Uses this 'Bressenham type' algo. I like the shape better than vanilla bresen
https://web.engr.oregonstate.edu/~sllu/bcircle.pdf
 */

public class BresenhamCircle {

    int RADIUS;
    BresenhamCircle(int RADIUS) {
        this.RADIUS = RADIUS;
    }

    public List<Tuple<Integer,Integer>> getCircle() {
        //Bresenham Circle
        List<Tuple<Integer,Integer>> tupleList = new ArrayList<>();

        int x = RADIUS, y = 0;
        int xchange = 1 - 2 * RADIUS;
        int ychange = 1;
        int radiuserror = 0;



        while (x >= y) {

            //all 8 octants of x,y
            tupleList.add(new Tuple<>(x, y));
            tupleList.add(new Tuple<>(-x, y));
            tupleList.add(new Tuple<>(x, -y));
            tupleList.add(new Tuple<>(-x, -y));
            tupleList.add(new Tuple<>(y, x));
            tupleList.add(new Tuple<>(-y, x));
            tupleList.add(new Tuple<>(y, -x));
            tupleList.add(new Tuple<>(-y, -x));

            y++;
            radiuserror = radiuserror + ychange;
            ychange = ychange + 2;
            if (2 * radiuserror + xchange > 0) {
                x--;
                radiuserror = radiuserror + xchange;
                xchange = xchange + 2;


            }
        }
        return tupleList;
    }

}
