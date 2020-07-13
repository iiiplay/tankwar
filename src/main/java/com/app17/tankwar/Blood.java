package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;

public class Blood extends GameObject {

    public Blood(int x, int y,Image image) {
        super(x,y,image);
        live=true;
    }


    @Override
    public void draw(Graphics g)
    {
        if(!live)
            return;

        g.drawImage(image,x,y,null);
    }

}
