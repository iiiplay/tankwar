package com.app17.tankwar;

import com.app17.tankwar.gameobject.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameClient extends Client {

    //取得GameClient實例
    private static final GameClient INSTANCE = new GameClient();

    private Tank playerTank;

    private List<Tank> enemyTanks;

    private List<Wall> walls;

    private List<Missile> missiles;

    private List<Explosion> explosions;

    private Blood blood;

    private final AtomicInteger enemyKilled = new AtomicInteger(0);

    private final static Random random = new Random();

    public Image bloodImg;
    public Image[] explosionImg = new Image[11];
    public Image wallImg;
    public int fps=50;

    public GameClient() {
        init();
    }

    @Override
    public void init(){
        bloodImg = Tools.getImage("blood.png");
        for (int i = 0; i < explosionImg.length; i++) {
            explosionImg[i] = Tools.getImage(i + ".gif");
        }

        wallImg = Tools.getImage("brick.png");
        setPreferredSize(new Dimension(800, 600));
        playerTank = new PlayerTank(400, 50,"tank", Direction.DOWN,false);
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
        this.enemyTanks = new CopyOnWriteArrayList<>();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                enemyTanks.add(new Tank(200 + j * 120, 400 + 40 * i,
                        "eTank",Direction.UP, true));
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ((PlayerTank)playerTank).keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ((PlayerTank)playerTank).keyReleased(e);
    }

    @Override
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

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


//        if (playerTank.isLive()) {
//            for (Tank tank : getEnemyTanks()) {
//                tank.actRandomly();
//            }
//        }
    }

    public static void main(String[] args) {
        GameClient client = GameClient.getInstance();
        JFrame frame=client.getFrame();

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

        client.loop(50);
    }


    void restart() {
        if (!playerTank.isLive()) {
            playerTank = new PlayerTank(400, 100,"tank", Direction.DOWN,false);
            this.initEnemyTank();
        }
    }

    public static GameClient getInstance() {
        return INSTANCE;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public PlayerTank getPlayerTank() {
        return (PlayerTank)playerTank;
    }

    public List<Missile> getMissiles() {
        return missiles;
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
