package net.tianzx;

import java.awt.*;

/**
 * Created by tianzx on 2016/6/4.
 */
public class Missile {
    public static final int XSPEED =10;
    public static final int YSPEED =10;
    private int x ,y;
    private Tank.Direction direction;

    public Missile(int x, int y, Tank.Direction direction){
        super();
        this.x = x;
        this.y=y;
        this.direction = direction;
    }

    public void draw(Graphics graphics){
        Color color =  graphics.getColor();
        graphics.setColor(color.RED);
        graphics.fillOval(x,y,XSPEED,YSPEED);
        graphics.setColor(color);

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
        }
    }
}
