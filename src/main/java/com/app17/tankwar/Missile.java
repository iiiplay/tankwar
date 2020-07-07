package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;

public class Missile extends GameObject {
    private static final int SPEED = 10;

    private Direction direction;


    Missile(int x, int y, boolean enemy, Direction direction) {
        super(x, y, (Image) null);
        this.direction = direction;
        this.enemy = enemy;
        live = true;
    }

    void move() {
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }

    public void addExplosion() {
        GameClient.getInstance().addExplosion(new Explosion(x, y, GameClient.getInstance().explosionImg));
        Tools.playAudio("explode.wav");
    }


    @Override
    public void update(Graphics g) {
        GameClient client = GameClient.getInstance();
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            live = false;
            return;
        }

        for (Wall wall : client.getWalls()) {
            if (live && getRectangle().intersects(wall.getRectangle())) {
                live = false;
                break;
            }
        }

        if (enemy) {
            Tank playTank = client.getPlayerTank();
            if (live && getRectangle().intersects(playTank.getRectangleForHitDetection())) {
                addExplosion();
                playTank.setHp(playTank.getHp() - 20);
                if (playTank.getHp() <= 0) {
                    playTank.setLive(false);
                }
                live = false;
            }
        } else {
            for (Tank tank : client.getEnemyTanks()) {
                if (live && getRectangle().intersects(tank.getRectangleForHitDetection())) {
                    addExplosion();
                    tank.setLive(false);
                    live = false;
                    break;
                }
            }
        }

        if (live) {
            draw(g);
        }

    }

    private Image getImage() {
        return direction.getImage("missile");
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), x, y, null);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

}
