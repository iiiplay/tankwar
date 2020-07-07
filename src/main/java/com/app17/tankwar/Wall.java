package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;

public class Wall extends GameObject {

    private boolean horizontal;
    private int bricks;

    public Wall(int x, int y, Image image, int bricks, boolean horizontal) {
        super(x, y, image);
        this.horizontal = horizontal;
        this.bricks = bricks;
        live=true;
    }


    public Rectangle getRectangle() {
        return horizontal ? new Rectangle(x, y, bricks * width,
                image.getHeight(null)) : new Rectangle(x, y, width,
                height * bricks);
    }


    public void draw(Graphics g) {

        if (horizontal) {
            for (int i = 0; i < bricks; i++) {
                g.drawImage(image, x + i * width,
                        y, null);
            }
        } else {
            for (int i = 0; i < bricks; i++) {
                g.drawImage(image, x,
                        y + i * height, null);
            }
        }
    }
}
