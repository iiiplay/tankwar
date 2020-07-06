package com.app17.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {

    private static final int MOVE_SPEED = 5;

    private int x;
    private int y;
    private Direction direction;
    private boolean stopped;
    boolean enemy;

    private boolean live = true;
    private int hp = 100;


    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    public Tank(int x, int y, Direction direction) {
        this(x, y, direction, false);
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    void move() {
        if (stopped)
            return;

        x += direction.xFactor * MOVE_SPEED;
        y += direction.yFactor * MOVE_SPEED;

    }

    Image getImage() {
        String prefix = enemy ? "e" : "";
        return direction.getImage(prefix + "tank");
    }

    private boolean up, down, left, right;

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_A:
                superFire();
                break;

        }
    }

    private void superFire() {
        for (Direction direction : Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);

            GameClient.getInstance().getMissiles().add(missile);
        }

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";

        Tools.playAudio(audioFile);
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);

        GameClient.getInstance().getMissiles().add(missile);
        Tools.playAudio("shoot.wav");
    }


    private void determineDirection() {

        //預設為暫停狀態
        this.stopped = false;

        if (!up && !left && !down && !right) {
            this.stopped = true;
            return;
        }

        if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
        else if (up && !left && !down && right) this.direction = Direction.RIGHT_UP;
        else if (up && !left && !down && !right) this.direction = Direction.UP;
        else if (!up && !left && down && !right) this.direction = Direction.DOWN;
        else if (!up && left && down && !right) this.direction = Direction.LEFT_DOWN;
        else if (!up && !left && down && right) this.direction = Direction.RIGHT_DOWN;
        else if (!up && left && !down && !right) this.direction = Direction.LEFT;
        else if (!up && !left && !down && right) this.direction = Direction.RIGHT;


    }

    void draw(Graphics g) {

        if (!live) {

        }


        int oldX = x, oldY = y;

        determineDirection();
        move();
        if (x < 0) {
            x = 0;
        } else if (x > 800 - this.getImage().getWidth(null)) {
            x = 800 - this.getImage().getWidth(null);
        }

        if (y < 0) {
            y = 0;
        } else if (y > 600 - this.getImage().getHeight(null)) {
            y = 600 - this.getImage().getHeight(null);
        }

        Rectangle rec = getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (rec.intersects((wall.getRectangle()))) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        for (Tank tank : GameClient.getInstance().getEnemyTanks()) {
            if (rec.intersects((tank.getRectangle()))) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        g.drawImage(this.getImage(), this.getX(), this.getY(), null);
    }

    //取得坦克區間
    Rectangle getRectangle() {
        return new Rectangle(x, y, this.getImage().getWidth(null),
                this.getImage().getHeight(null));
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;

        }
    }
}

