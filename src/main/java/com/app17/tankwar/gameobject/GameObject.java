package com.app17.tankwar.gameobject;

import com.app17.tankwar.GameClient;

import java.awt.*;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int oldX;
    protected int oldY;
    protected int speed;
    protected int step;

    protected boolean stopped;
    protected boolean enemy;
    protected boolean live;
    protected Image image;
    protected Image[] images;
    protected int width;
    protected int height;

    public GameObject() {
        enemy = false;
        live = false;
        stopped = false;
        step = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameObject(int x, int y, Image image) {
        this();
        this.x = x;
        this.y = y;
        this.image = image;
        if (image != null) {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
    }


    public GameObject(int x, int y, Image[] images) {
        this();
        this.x = x;
        this.y = y;
        this.images = images;
        if (images != null) {
            width = images[0].getWidth(null);
            height = images[1].getHeight(null);
        }
    }


    public void update(Graphics g) {
        if (live)
            draw(g);
    }

    public abstract void draw(Graphics g);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, image.getWidth(null),
                image.getHeight(null));
    }


}
