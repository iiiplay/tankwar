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
        String prefix = "missile";
        switch (direction) {
            case UP:
                return Tools.getImage("missileU.gif");
            case DOWN:
                return Tools.getImage("missileD.gif");
            case LEFT:
                return Tools.getImage("missileL.gif");
            case RIGHT:
                return Tools.getImage("missileR.gif");
            case UP_RIGHT:
                return Tools.getImage("missileRU.gif");
            case UP_LEFT:
                return Tools.getImage("missileLU.gif");
            case DOWN_LEFT:
                return Tools.getImage("missileLD.gif");
            case DOWN_RIGHT:
                return Tools.getImage("missileRD.gif");
        }

        return null;
    }

    void move() {
        switch (direction) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;

            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP_RIGHT:
                y -= SPEED;
                x += SPEED;
                break;
            case UP_LEFT:
                y -= SPEED;
                x -= SPEED;
                break;
            case DOWN_LEFT:
                x -= SPEED;
                y += SPEED;
                break;
            case DOWN_RIGHT:
                x += SPEED;
                y += SPEED;
                break;
        }
    }

    public void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600)
            return;
        g.drawImage(getImage(), x, y, null);
    }
}
