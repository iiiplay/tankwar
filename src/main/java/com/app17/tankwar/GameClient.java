package com.app17.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent {

    private Tank playerTank;

    private List<Tank> enemyTanks;

    private GameClient() {
        this.setPreferredSize(new Dimension(800, 600));
        this.playerTank = new Tank(400, 100, Direction.DOWN);

        enemyTanks = new ArrayList<>(12);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                enemyTanks.add(new Tank(200 + j * 120, 400 + 40 * i, Direction.UP, true));
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        playerTank.draw(g);
        for (Tank tank : enemyTanks) {
            tank.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("來了!第一個坦克大戰!!");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = new GameClient();
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
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
