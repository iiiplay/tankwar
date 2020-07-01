package com.app17.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private int x;
    private int y;
    private Direction direction;
    private boolean stopped;

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

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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
        switch (direction) {
            case UP:
                return new ImageIcon("assets/images/tankU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/tankD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/tankR.gif").getImage();
            case UP_RIGHT:
                return new ImageIcon("assets/images/tankRU.gif").getImage();
            case UP_LEFT:
                return new ImageIcon("assets/images/tankLU.gif").getImage();
            case DOWN_LEFT:
                return new ImageIcon("assets/images/tankLD.gif").getImage();
            case DOWN_RIGHT:
                return new ImageIcon("assets/images/tankRD.gif").getImage();
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
