package com.app17.tankwar;

import java.awt.*;

public enum Direction {

    UP(0, "U", 0, -1),
    DOWN(1, "D", 0, 1),
    LEFT(2, "L", -1, 0),
    RIGHT(3, "R", 1, 0),
    LEFT_UP(4, "LU", -1, -1),
    RIGHT_UP(5, "RU", 1, -1),
    LEFT_DOWN(6, "LD", -1, 1),
    RIGHT_DOWN(7, "RD", 1, 1);

    private final String abbrev;
    final int index, xFactor, yFactor;

    Direction(int index, String abbrev, int xFactor, int yFactor) {
        this.index = index;
        this.abbrev = abbrev;
        this.xFactor = xFactor;
        this.yFactor = yFactor;

    }


    Image getImage(String prefix) {
        //新坦克造型
//        if(prefix.equals("itank") || prefix.equals("etank"))
//            return Tools.getImage("tank\\"+prefix + abbrev + ".png");

        //return Tools.getImage(prefix + abbrev + ".gif");
        return Tools.getImage(prefix + abbrev + ".png");

    }

}
