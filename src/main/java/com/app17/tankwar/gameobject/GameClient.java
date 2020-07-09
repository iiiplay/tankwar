package com.app17.tankwar.gameobject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class GameClient extends JComponent implements Runnable {
    private Thread thread;
    private int FPS;
    protected boolean gameStop;
    private int screenWidth;
    private int screenHeight;
    protected static GameClient instance;
    protected List<GameObject> gameObjects = new CopyOnWriteArrayList<>();



    public int getScreenWidth() {
        return screenWidth;
    }

    public void addGameObject(GameObject object){
        gameObjects.add(object);
    }

    public void addGameObjects(List objects){
        gameObjects.addAll(objects);
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public GameClient() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        setScreen(800, 600);
        setFPS(25);
        thread = new Thread(this);
        thread.start();
    }


    public void setScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
        init();

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
