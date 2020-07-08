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

    private String imageName;

    public Tank(int x, int y, String imageName, Direction direction) {
        this(x, y, imageName, direction, false);
    }

    public Tank(int x, int y, String imageName, Direction direction, boolean enemy) {
        super(x, y, (Image) null);
        this.direction = direction;
        this.enemy = enemy;
        this.imageName=imageName;
        hp = MAX_HP;
        live = true;
    }

    public void move() {
        if (stopped)
            return;

        x += direction.xFactor * MOVE_SPEED;
        y += direction.yFactor * MOVE_SPEED;
    }

    protected Image getImage() {
        return direction.getImage(imageName);
    }


    //取得坦克主體區間
    public Rectangle getRectangle() {
        return new Rectangle(x, y, this.getImage().getWidth(null),
                this.getImage().getHeight(null));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), null);
    }

    protected void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);

        GameClient.getInstance().addMissile(missile);
        Tools.playAudio("shoot.wav");
    }


    private final Random random = new Random();
    private int step = random.nextInt(12) + 3;

    public void actRandomly() {
        Direction[] dirs = Direction.values();
        if (step == 0) {
            step = random.nextInt(12) + 3;
            this.direction = dirs[random.nextInt(dirs.length)];
            if (random.nextBoolean()) {
                this.fire();
            }
        }
        step--;
    }

    @Override
    public void update(Graphics g) {
        int oldX = x, oldY = y;
        GameClient client = GameClient.getInstance();

        if(enemy)
            actRandomly();

        move();

        if (x < 0) {
            x = 0;
        } else if (x > 800 - getImage().getWidth(null)) {
            x = 800 - getImage().getWidth(null);
        }

        if (y < 0) {
            y = 0;
        } else if (y > 600 - getImage().getHeight(null)) {
            y = 600 - getImage().getHeight(null);
        }

        Rectangle rec = getRectangle();
        for (Wall wall : client.getWalls()) {
            if (rec.intersects((wall.getRectangle()))) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        for (Tank tank : client.getEnemyTanks()) {
            if (tank != this && rec.intersects((tank.getRectangle()))) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        if (enemy && rec.intersects(client.getPlayerTank().getRectangle())) {
            x = oldX;
            y = oldY;
        }

        if (!enemy) {
            Blood blood = client.getBlood();
            if (blood.isLive() && this.getRectangle().intersects(client.getBlood().getRectangle())) {
                this.hp = MAX_HP;
                Tools.playAudio("revive.wav");
                blood.setLive(false);
            }
        }

        if(live){
            draw(g);
        }
    }
}
