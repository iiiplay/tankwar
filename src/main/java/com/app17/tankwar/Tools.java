package com.app17.tankwar;

import javax.swing.*;
import java.awt.*;

public class Tools {
    public static Image getImage(String imageName) {
        Image image = new ImageIcon("assets/images/" + imageName).getImage();

        return image;
    }
}
