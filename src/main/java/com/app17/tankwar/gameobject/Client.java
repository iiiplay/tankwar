package com.app17.tankwar.gameobject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public abstract class Client extends JComponent implements Runnable {

    private Thread thread;
    private int FPS;
    protected boolean gameStop;

    public Client() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        setFPS(25);
        thread = new Thread(this);
        thread.start();
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);

    public abstract void init();

    public abstract void update(Graphics g);

    @Override
    public void run() {
        long startTime;
        long URDTimeMillis;
        long waitTime;
        long targetTime = 1000 / FPS;

        while (!gameStop) {

            //主遊戲循環
            startTime = System.nanoTime();
            repaint();
            URDTimeMillis = (System.nanoTime() - startTime) / 10000000;
            waitTime = targetTime - URDTimeMillis;
            try {
                Thread.sleep(waitTime);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JFrame getFrame(String title, Image icon) {
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
