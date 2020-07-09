package com.app17.tankwar;

import java.awt.*;
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

        g.drawImage(CamelPet.PET_IMAGE, this.x - CamelPet.PET_IMAGE.getWidth(null) - CamelPet.DISTANCE
                , this.y, null);

        super.draw(g);
    }

    @Override
    public void update(Graphics g) {
        determineDirection();
        super.update(g);
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
}
