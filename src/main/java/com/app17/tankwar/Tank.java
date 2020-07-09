package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject {
    public static final int MOVE_SPEED = 5;
    public static final int MAX_HP = 100;

    protected Direction direction;
    protected int hp;
    protected boolean up, down, left, right;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    boolean isDying() {
        return hp <= MAX_HP * 0.2;
    }


    private final Random random = new Random();

    public Tank(int x, int y, Image[] images, Direction direction) {
        this(x, y, images, direction, false);
    }

    public Tank(int x, int y, Image[] images, Direction direction, boolean enemy) {
        super(x, y, images);
        //目前方向
        this.direction = direction;
        this.enemy = enemy;
        hp = MAX_HP;
        live = true;
        step = random.nextInt(12) + 3;
    }

    public void move() {
        if (stopped)
            return;

        oldX = x;
        oldY = y;
        x += direction.xFactor * MOVE_SPEED;
        y += direction.yFactor * MOVE_SPEED;
    }

    protected Image getImage() {
        return images[direction.index];
    }


    //取得坦克主體區間
    public Rectangle getRectangle() {
        return new Rectangle(x, y, this.getImage().getWidth(null),
                this.getImage().getHeight(null));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), null);
    }

    protected void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, TankGame.getInstance().missileImg,
                enemy, direction);

        TankGame.getInstance().addMissile(missile);
        Tools.playAudio("shoot.wav");
    }


    //亂數移動跟開槍
    public void actRandomly() {
        Direction[] dirs = Direction.values();

        if (step == 0) {
            step = random.nextInt(12) + 3;
            this.direction = dirs[random.nextInt(dirs.length)];
            if (random.nextBoolean()) {
                this.fire();
            }
        }
        if (random.nextInt(2) == 1) {
            step--;
        }
    }


    @Override
    public void update(Graphics g) {

        move();
        detectCollision(this);

        if (enemy) {
            actRandomly();
        }

        if (live) {
            draw(g);
        }
    }


    //偵測碰撞
    public static void detectCollision(GameObject object) {
        TankGame client = TankGame.getInstance();
        Tank tank = (Tank) object;
        Rectangle rectangle = tank.getRectangle();

        if (tank.x < 0) {
            tank.x = 0;
        } else if (tank.x > client.getScreenWidth() - tank.getImage().getWidth(null)) {
            tank.x = client.getScreenWidth() - tank.getImage().getWidth(null);
        }

        if (tank.y < 0) {
            tank.y = 0;
        } else if (tank.y > client.getScreenHeight() - tank.getImage().getHeight(null)) {
            tank.y = client.getScreenHeight() - tank.getImage().getHeight(null);
        }

        for (Wall wall : client.getWalls()) {
            if (rectangle.intersects((wall.getRectangle()))) {
                tank.x = tank.oldX;
                tank.y = tank.oldY;
                break;
            }
        }

        for (Tank enemyTank : client.getEnemyTanks()) {
            if (enemyTank != tank && rectangle.intersects((enemyTank.getRectangle()))) {
                tank.x = tank.oldX;
                tank.y = tank.oldY;
                break;
            }
        }

        if (tank.enemy && rectangle.intersects(client.getPlayerTank().getRectangle())) {
            tank.x = tank.oldX;
            tank.y = tank.oldY;
        }

        if (!tank.enemy) {
            Blood blood = client.getBlood();
            if (blood.isLive() && tank.getRectangle().intersects(client.getBlood().getRectangle())) {
                tank.hp = MAX_HP;
                Tools.playAudio("revive.wav");
                blood.setLive(false);
            }
        }
    }
}
