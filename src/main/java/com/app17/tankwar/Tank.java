package com.app17.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

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
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }

    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);

        GameClient.getInstance().getMissiles().add(missile);
        Media sound = new Media(new File("assets/audios/shoot.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
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
    public Rectangle getRectangle() {
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

