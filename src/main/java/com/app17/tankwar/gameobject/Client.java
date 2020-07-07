package com.app17.tankwar.gameobject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public abstract class Client extends JComponent  {


    public Client(String title,ImageIcon icon) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        JFrame frame=new JFrame();
        frame.setTitle(title);
        frame.setIconImage(icon.getImage());
        frame.repaint();
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyReleased(e);
            }
        });
        frame.setVisible(true);
    }

    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    public abstract void initImage();
    public abstract void update(Graphics g);

    public void init(){
        initImage();
    }

    protected void paintComponent(Graphics g) {
        update(g);
    }
}
