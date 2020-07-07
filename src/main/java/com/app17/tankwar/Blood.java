package com.app17.tankwar;

import java.awt.*;

public class Blood extends GameObject{

    public Blood(int x, int y,Image image) {
        super(x,y,image);
        live=true;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g)
    {
        update();
        if(!live)
            return;

        g.drawImage(image,x,y,null);
    }

}
