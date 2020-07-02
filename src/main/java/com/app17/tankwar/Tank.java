package com.app17.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private int x;
    private int y;
    private Direction direction;
    private boolean stopped;
    private boolean enemy;


    public Tank(int x, int y, Direction direction) {
        this(x, y, direction, false);
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    void move() {
        if (stopped)
            return;

        switch (direction) {
            case UP:
                y -= 5;
                break;
            case DOWN:
                y += 5;
                break;
            case LEFT:
                x -= 5;
                break;
            case RIGHT:
                x += 5;
                break;
            case UP_RIGHT:
                y -= 5;
                x += 5;
                break;
            case UP_LEFT:
                y -= 5;
                x -= 5;
                break;
            case DOWN_LEFT:
                x -= 5;
                y += 5;
                break;
            case DOWN_RIGHT:
                x += 5;
                y += 5;
                break;
        }
    }

    Image getImage() {
        String prefix = enemy ? "e" : "";
        switch (direction) {
            case UP:
                return Tools.getImage(prefix + "tankU.gif");
            case DOWN:
                return Tools.getImage(prefix + "tankD.gif");
            case LEFT:
                return Tools.getImage(prefix + "tankL.gif");
            case RIGHT:
                return Tools.getImage(prefix + "tankR.gif");
            case UP_RIGHT:
                return Tools.getImage(prefix + "tankRU.gif");
            case UP_LEFT:
                return Tools.getImage(prefix + "tankLU.gif");
            case DOWN_LEFT:
                return Tools.getImage(prefix + "tankLD.gif");
            case DOWN_RIGHT:
                return Tools.getImage(prefix + "tankRD.gif");
        }

        return null;
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
        }

    }


    private void determineDirection() {

        //預設為暫停狀態
        this.stopped = false;

        if (!up && !left && !down && !right) {
            this.stopped = true;
            return;
        }

        if (up && left && !down && !right) this.direction = Direction.UP_LEFT;
        else if (up && !left && !down && right) this.direction = Direction.UP_RIGHT;
        else if (up && !left && !down && !right) this.direction = Direction.UP;
        else if (!up && !left && down && !right) this.direction = Direction.DOWN;
        else if (!up && left && down && !right) this.direction = Direction.DOWN_LEFT;
        else if (!up && !left && down && right) this.direction = Direction.DOWN_RIGHT;
        else if (!up && left && !down && !right) this.direction = Direction.LEFT;
        else if (!up && !left && !down && right) this.direction = Direction.RIGHT;


    }

    void draw(Graphics g) {
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

        g.drawImage(this.getImage(), this.getX(), this.getY(), null);
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
