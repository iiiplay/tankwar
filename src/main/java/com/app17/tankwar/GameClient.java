package com.app17.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameClient extends JComponent {

    //取得GameClient實例
    private static final GameClient INSTANCE = new GameClient();

    private Tank playerTank;

    private List<Tank> enemyTanks;

    private List<Wall> walls;

    private List<Missile> missiles;

    private List<Explosion> explosions;


    public static GameClient getInstance() {
        return INSTANCE;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    void addMissile(Missile missile) {
        missiles.add(missile);
    }

    void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    private GameClient() {

        this.setPreferredSize(new Dimension(800, 600));
        this.playerTank = new Tank(400, 100, Direction.DOWN);
        this.missiles = new CopyOnWriteArrayList<>();
        this.explosions = new CopyOnWriteArrayList<>();


        //將陣列轉換成集合
        walls = Arrays.asList(
                new Wall(200, 140, 15, true),
                new Wall(200, 540, 15, true),
                new Wall(100, 80, 15, false),
                new Wall(700, 80, 15, false));


        this.initEnemyTank();
    }

    private void initEnemyTank() {
        this.enemyTanks = new CopyOnWriteArrayList<>();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                enemyTanks.add(new Tank(200 + j * 120, 400 + 40 * i, Direction.UP, true));
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        if (!playerTank.isLive()) {
            g.setFont(new Font(null, Font.BOLD, 100));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 100, 300);

            g.setFont(new Font(null, Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("PRESS F2 TO RESTART", 100, 500);
            return;

        }
        playerTank.draw(g);

        enemyTanks.removeIf(t -> !t.isLive());
        if (enemyTanks.isEmpty()) {
            this.initEnemyTank();
        }
        for (Tank tank : enemyTanks) {
            tank.draw(g);
        }
        for (Wall wall : walls) {
            wall.draw(g);
        }

        missiles.removeIf(m -> !m.isLive());
        for (Missile missile : missiles) {
            missile.draw(g);
        }

        explosions.removeIf(e -> !e.isLive());
        for (Explosion explosion : explosions) {
            explosion.draw(g);
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        JFrame frame = new JFrame();
        frame.setTitle("來了!第一個坦克大戰!!");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = GameClient.getInstance();
        frame.repaint();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);

            }
        });
        frame.setVisible(true);

        while (true) {
            client.repaint();

            if (client.playerTank.isLive()) {
                for (Tank tank : client.getEnemyTanks()) {
                    tank.actRandomly();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void restart() {
        if(!playerTank.isLive()){
            playerTank = new Tank(400, 100, Direction.DOWN);
            this.initEnemyTank();
        }
    }
}
