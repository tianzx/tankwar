package net.tianzx;

import net.tianzx.Tank;

import java.awt.*;

public class Missile {
    public static final int XSPEED = 10;
    public static final int YSPEED = 10;

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    int x, y;

    Tank.Direction dir;
    private boolean live = true;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        move();
    }

    private void move() {
        switch (dir) {
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
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }

        if(x<0 || y<0 || x>TankClient.GAME_WIDTH ||y>TankClient.GAME_HEIGHT){
            live = false;
        }
    }

    public boolean isbLive() {
        return live;
    }

    public void setbLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public boolean hitTank(Tank tank){
        if(this.getRect().intersects(tank.getRect())){
            tank.setLive(false);
            return true;
        }else {
            return false;
        }
    }
}
