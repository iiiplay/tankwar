package com.app17.tankwar;

import com.app17.tankwar.gameobject.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank extends GameObject {

    private static final int MOVE_SPEED = 5;
    private static final int MAX_HP = 100;

    private Direction direction;
    private int hp;
    private boolean up, down, left, right;

    public Tank(int x, int y, Direction direction) {
        this(x, y, direction, false);
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        super(x,y,(Image)null);
        this.direction = direction;
        this.enemy = enemy;
        hp=MAX_HP;
        live=true;
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

    @Override
    public Rectangle getRectangle() {
        if (enemy) {
            return new Rectangle(x, y, this.getImage().getWidth(null),
                    this.getImage().getHeight(null));
        } else {
            Image petImage = Tools.getImage("pet-camel.gif");
            int delta = petImage.getWidth(null) + 4;
            return new Rectangle(x - delta, y,
                    this.getImage().getWidth(null) + delta,
                    this.getImage().getHeight(null));
        }
    }
    //取得坦克主體區間
    Rectangle getRectangleForHitDetection() {
        return new Rectangle(x, y, this.getImage().getWidth(null),
                this.getImage().getHeight(null));
    }

    @Override
    public void update(Graphics g) {
        int oldX = x, oldY = y;
        GameClient client = GameClient.getInstance();

        if (!enemy) {
            determineDirection();
        }

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

    @Override
    public void draw(Graphics g) {

        if (!enemy) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y - 10, this.getImage().getWidth(null), 10);
            g.setColor(Color.RED);
            int width = hp * this.getImage().getWidth(null) / 100;
            g.fillRect(x, y - 10, width, 10);
            Image petImage = Tools.getImage("pet-camel.gif");
            g.drawImage(petImage, this.x - petImage.getWidth(null) - 4, this.y, null);
        }


        g.drawImage(this.getImage(), this.getX(), this.getY(), null);
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
            case KeyEvent.VK_F2:
                GameClient.getInstance().restart();

        }
    }

    private final Random random = new Random();
    private int step = random.nextInt(12) + 3;


    private void superFire() {
        for (Direction direction : Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);

            GameClient.getInstance().addMissile(missile);
        }

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";

        Tools.playAudio(audioFile);
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);

        GameClient.getInstance().addMissile(missile);
        Tools.playAudio("shoot.wav");
    }

    boolean isDying() {
        return hp <= MAX_HP * 0.2;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

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
}


