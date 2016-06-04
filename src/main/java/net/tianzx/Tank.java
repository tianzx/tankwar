package net.tianzx;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by tianzx on 2016/6/2.
 */
public class Tank {

    public static final int XSPEED = 5;
    public static final int YSPEED = 5;
    public static final int WIDTH = 30;
    public static final int HEIGHT= 30;

    TankClient tankClient;

    private int x, y;

    private boolean bL = false, bU = false, bR = false, bD = false;

    public Tank(int x, int y, TankClient tc) {
        this(x, y);
        this.tankClient = tc;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public enum Direction {
        L, LU, U, RU, R, RD, D, LD, STOP
    }

    ;
    public Direction direction = Direction.STOP;

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        System.err.println("painting..............");
        move();
    }

    private void move() {
        switch (direction) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += XSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
                tankClient.missile = fire();
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locateDirection();
    }

    void locateDirection() {
        if (bL && !bU && !bR && !bD) direction = Direction.L;
        else if (bL && bU && !bR && !bD) direction = Direction.LU;
        else if (!bL && bU && !bR && !bD) direction = Direction.U;
        else if (!bL && bU && bR && !bD) direction = Direction.RU;
        else if (!bL && !bU && bR && !bD) direction = Direction.R;
        else if (!bL && !bU && bR && bD) direction = Direction.RD;
        else if (!bL && !bU && !bR && bD) direction = Direction.D;
        else if (bL && !bU && !bR && bD) direction = Direction.LD;
        else if (!bL && !bU && !bR && !bD) direction = Direction.STOP;

    }

    public Missile fire() {
        int x = this.x + WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + HEIGHT/2 - Missile.HEIGHT/2;

        Missile missile = new Missile(x, y, direction);
        return missile;
    }
}