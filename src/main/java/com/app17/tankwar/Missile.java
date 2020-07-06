package com.app17.tankwar;

import java.awt.*;

public class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    //唯一方向性
    private final Direction direction;
    private final boolean enemy;

    public Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    private Image getImage() {
        return direction.getImage("missile");
    }

    void move() {
        x+=direction.xFactor*SPEED;
        y+=direction.yFactor*SPEED;
    }

    public void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600)
            return;
        g.drawImage(getImage(), x, y, null);
    }
}
