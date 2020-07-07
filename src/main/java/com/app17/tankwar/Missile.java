package com.app17.tankwar;

import java.awt.*;

public class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    //唯一方向性
    private final Direction direction;
    private final boolean enemy;

    private boolean live = true;

    boolean isLive() {
        return live;
    }

    private void setLive(boolean live) {
        this.live = live;
    }

    Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    private Image getImage() {
        return direction.getImage("missile");
    }

    void move() {
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }

    void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            this.live = false;

            return;
        }

        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (this.getRectangle().intersects(wall.getRectangle())) {
                this.live = false;

                return;
            }
        }

        if (enemy) {
            Tank playerTank = GameClient.getInstance().getPlayerTank();
            if (this.getRectangle().intersects(playerTank.getRectangleForHitDetection())) {
                addExplosion();
                playerTank.setHp(playerTank.getHp() - 20);
                if (playerTank.getHp() <= 0) {
                    playerTank.setLive(false);
                }
                this.live = false;
            }

        } else {
            for (Tank tank : GameClient.getInstance().getEnemyTanks()) {
                if (this.getRectangle().intersects(tank.getRectangleForHitDetection())) {
                    addExplosion();
                    tank.setLive(false);
                    this.live = false;

                    break;
                }
            }
        }
        g.drawImage(getImage(), x, y, null);
    }

    private Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    private void addExplosion() {
        GameClient.getInstance().addExplosion(new Explosion(x, y));
        Tools.playAudio("explode.wav");
    }
}
