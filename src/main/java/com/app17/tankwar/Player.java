package com.app17.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Player extends Tank implements CamelPet {

    public Player(int x, int y, Image[] images, Direction direction, boolean enemy) {
        super(x, y, images, direction, enemy);
        step = 0;
    }

    public void superFire() {
        for (Direction direction : Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, TankGame.getInstance().missileImg, enemy, direction);

            TankGame.getInstance().addMissile(missile);
        }

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";

        Tools.playAudio(audioFile);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y - 10, this.getImage().getWidth(null), 10);
        g.setColor(Color.RED);
        int width = hp * this.getImage().getWidth(null) / 100;
        g.fillRect(x, y - 10, width, 10);



        super.draw(g);
    }

    @Override
    public void update(Graphics g) {
        determineDirection();
        super.update(g);
        drawPet(g);
    }

    //偵測行走方向
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

    //取得坦克區間
    public Rectangle getRectangle() {
        int delta = PET_IMAGE.getWidth(null) + CamelPet.DISTANCE;
        return new Rectangle(x - delta, y,
                this.getImage().getWidth(null) + delta,
                this.getImage().getHeight(null));
    }

    //取得坦克主體區間
    public Rectangle getRectangleForHitDetection() {
        int delta = PET_IMAGE.getWidth(null) + CamelPet.DISTANCE;
        return new Rectangle(x - CamelPet.PET_IMAGE.getWidth(null) - CamelPet.DISTANCE, y,
                this.getImage().getWidth(null),
                this.getImage().getHeight(null));
    }

    @Override
    public void drawPet(Graphics g) {
        g.drawImage(CamelPet.PET_IMAGE, this.x - CamelPet.PET_IMAGE.getWidth(null) - CamelPet.DISTANCE
                , this.y, null);
    }
}
