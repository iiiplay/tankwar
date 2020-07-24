package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TankGame extends GameClient {

    private Player playerTank;
    private List<Tank> enemyTanks;
    private List<Wall> walls;
    private List<Missile> missiles;
    private List<Explosion> explosions;
    private Blood blood;


    public Image bloodImg;
    public Image[] explosionImg = new Image[5];
    public Image[] tankImg = new Image[8];
    public Image[] enemyTankImg = new Image[8];
    public Image[] missileImg = new Image[8];
    public Image wallImg;

    private final AtomicInteger enemyKilled = new AtomicInteger(0);
    private final static Random random = new Random();


    @Override
    public void keyPressed(KeyEvent e) {
        playerTank.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerTank.keyReleased(e);
        switch (e.getKeyCode()) {

            case KeyEvent.VK_F2:
                restart();
                break;
        }
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        bloodImg = Tools.getImage("blood.png");
        for (int i = 0; i < explosionImg.length; i++) {
            explosionImg[i] = Tools.getImage("blowup/" + i + ".png");
        }

        Direction[] direction = Direction.values();

        for (int i = 0; i < direction.length; i++) {
            tankImg[i] = direction[i].getImage("itank");
            enemyTankImg[i] = direction[i].getImage("etank");
            missileImg[i] = direction[i].getImage("missile");
        }

        wallImg = Tools.getImage("brick.png");
        playerTank = new Player(400, 50, tankImg, Direction.DOWN, false);
        missiles = new CopyOnWriteArrayList<>();
        explosions = new CopyOnWriteArrayList<>();
        blood = new Blood(400, 250, bloodImg);

        //將陣列轉換成集合
        walls = Arrays.asList(
                new Wall(280, 140, wallImg, 12, true),
                new Wall(280, 540, wallImg, 12, true),
                new Wall(100, 180, wallImg, 12, false),
                new Wall(700, 180, wallImg, 12, false));

        initEnemyTank();
    }

    private void initEnemyTank() {
        enemyTanks = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                enemyTanks.add(new Tank(200 + j * 120, 400 + 47 * i,
                        enemyTankImg, Direction.UP, true));
            }
        }
    }


    //取得同一個實體
    public static TankGame getInstance() {
        if (instance == null) {
            instance = new TankGame();
        }
        return (TankGame) instance;
    }


    @Override
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getScreenWidth(), getScreenHeight());

        //GameOver
        if (!playerTank.isLive()) {
            g.setFont(new Font(null, Font.BOLD, 100));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 100, 300);

            g.setFont(new Font(null, Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("PRESS F2 TO RESTART", 100, 500);
            return;
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font(null, Font.BOLD, 16));
        g.drawString("FPS:" + this.getFPS(), 10, 30);
        g.drawString("Missile:" + missiles.size(), 10, 50);
        g.drawString("Explosions:" + explosions.size(), 10, 70);
        g.drawString("Player Tank HP:" + playerTank.getHp(), 10, 90);
        g.drawString("Enemy Left:" + enemyTanks.size(), 10, 110);
        g.drawString("Enemy Killed:" + enemyKilled.get(), 10, 130);
        g.drawImage(Tools.getImage("tree.png"), 720, 10, null);
        g.drawImage(Tools.getImage("tree.png"), 10, 520, null);

        playerTank.update(g);

        if (playerTank.isDying() && random.nextInt(3) == 1) {
            blood.setLive(true);
        }

        if (blood.isLive()) {
            blood.update(g);
        }

        int count = enemyTanks.size();
        enemyTanks.removeIf(t -> !t.isLive());
        enemyKilled.addAndGet(count - enemyTanks.size());

        if (enemyTanks.isEmpty()) {
            this.initEnemyTank();
        }
        for (Tank tank : enemyTanks) {
            tank.update(g);
        }
        for (Wall wall : walls) {
            wall.update(g);
        }

        missiles.removeIf(m -> !m.isLive());
        for (Missile missile : missiles) {
            missile.update(g);
        }

        explosions.removeIf(e -> !e.isLive());
        for (Explosion explosion : explosions) {
            explosion.update(g);
        }
    }


    void restart() {
        if (!playerTank.isLive()) {
            playerTank = new Player(400, 100, tankImg, Direction.DOWN, false);
            initEnemyTank();
            missiles.clear();
            explosions.clear();
            enemyTanks.clear();
        }
    }

    public static void main(String[] args) {

        TankGame client = TankGame.getInstance();
        JFrame frame = client.getFrame("來了!第一個坦克大戰!!",
                new ImageIcon("assets/images/icon.png").getImage());

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.keyReleased(e);
            }
        });
    }


    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public Player getPlayerTank() {
        return playerTank;
    }

    public Blood getBlood() {
        return blood;
    }

    void addMissile(Missile missile) {
        missiles.add(missile);
    }

    void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }
}
