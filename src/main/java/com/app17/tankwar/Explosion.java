package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;

public class Explosion extends GameObject {


    public Explosion(int x, int y, Image[] images) {
        super(x, y, images);
        live = true;
    }

    @Override
    public void update(Graphics g) {
        if (++step >= images.length) {
            live = false;
            return;
        }
        draw(g);
    }


    @Override
    public void draw(Graphics g) {
        if (live) {
            g.drawImage(images[step], x, y, null);
        }
    }
}

