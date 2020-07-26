package com.app17.tankwar;

import java.awt.*;

public interface CamelPet {
    Image PET_IMAGE =Tools.getImage("pet-camel.png");
    int DISTANCE =4;

    void drawPet(Graphics g);
}
