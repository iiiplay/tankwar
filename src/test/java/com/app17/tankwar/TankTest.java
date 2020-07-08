package com.app17.tankwar;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TankTest {

    @Test
    /**
     * 側視圖片是否都能正常讀取
     */
    void getImage() {

        Direction[] directions=Direction.values();
        Image[] tankImg=new Image[directions.length];
        for(int i=0;i<directions.length;i++){
            tankImg[i]=directions[i].getImage("tank");
        }
        for (Direction direction : Direction.values()) {
            Tank tank = new PlayerTank(0, 0, tankImg,direction, false);

            assertTrue(tank.getImage().getWidth(null) > 0,
                    direction + " error!");


            Tank enemyTank = new Tank(0, 0,tankImg, direction, true);
            assertTrue(enemyTank.getImage().getWidth(null) > 0,
                    enemyTank.getClass().getName()+direction + " error!");
        }


    }
}