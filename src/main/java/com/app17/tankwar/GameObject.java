package com.app17.tankwar;

import java.awt.*;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int speed;
    protected boolean stopped;
    protected boolean enemy;
    protected boolean live;
    protected Image image;

    public GameObject(){
        enemy=false;
        live=false;
        stopped=false;
    }

    public GameObject(int x, int y, Image image) {
        this();
        this.x = x;
        this.y = y;
        this.image=image;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

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
