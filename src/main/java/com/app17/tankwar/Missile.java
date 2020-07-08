package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;

public class Missile extends GameObject {
    private static final int SPEED = 10;

    private Direction direction;


    Missile(int x, int y,Image[] images, boolean enemy, Direction direction) {
        super(x, y, images);
        this.direction = direction;
        this.enemy = enemy;
        live = true;
    }

    void move() {
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }

    public void addExplosion() {
        GameClient.getInstance().addExplosion(new Explosion(x, y,
                GameClient.getInstance().explosionImg));
        Tools.playAudio("explode.wav");
    }

    //偵測碰撞
    public static void detectCollision(GameObject object) {
        GameClient client = GameClient.getInstance();
        Missile missile=(Missile)object;
        if (missile.x < 0 || missile.x > 800 || missile.y < 0 || missile.y > 600) {
            missile.live = false;
            return;
        }

        for (Wall wall : client.getWalls()) {
            if (missile.live && missile.getRectangle().intersects(wall.getRectangle())) {
                missile.live = false;
                break;
            }
        }

        PlayerTank playTank = client.getPlayerTank();
        if (missile.enemy) {
            if (missile.live && missile.getRectangle().intersects(playTank.getRectangleForHitDetection())) {
                missile.addExplosion();
                playTank.setHp(playTank.getHp() - 20);
                if (playTank.getHp() <= 0) {
                    playTank.setLive(false);
                }
                missile.live = false;
            }
        } else {
            for (Tank tank : client.getEnemyTanks()) {
                if (missile.live && missile.getRectangle().intersects(tank.getRectangle())) {
                    missile.addExplosion();
                    tank.setLive(false);
                    missile.live = false;
                    break;
                }
            }
        }
    }

    @Override
    public void update(Graphics g) {

        move();
        detectCollision(this);

        if (live) {
            draw(g);
        }

    }

    private Image getImage() {
        return images[direction.index];
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
