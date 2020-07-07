package com.app17.tankwar.gameobject;

import com.app17.tankwar.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public abstract class Client extends JComponent  {
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    public abstract void init();
    public abstract void update(Graphics g);

    public Client(){
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    public void loop(int fps){
        while (true) {
            //主遊戲循環
            try {
                repaint();
                Thread.sleep(fps);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JFrame getFrame(){
        JFrame frame = new JFrame();
        frame.setTitle("來了!第一個坦克大戰!!");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        frame.add(this);
        frame.repaint();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);

        return frame;
    }

    protected void paintComponent(Graphics g) {
        update(g);
    }

}
