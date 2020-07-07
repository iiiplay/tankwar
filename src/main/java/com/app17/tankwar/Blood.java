package com.app17.tankwar;

import java.awt.*;

public class Blood {
    private int x,y;
    private boolean live;
    private final Image image;

    public Blood(int x, int y) {
        this.x = x;
        this.y = y;
        live=true;
        image=Tools.getImage("blood.png");
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    void draw(Graphics g){
        g.drawImage(image,x,y,null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, image.getWidth(null),
                image.getHeight(null));
    }
}
