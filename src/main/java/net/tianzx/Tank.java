package net.tianzx;

import net.tianzx.TankClient;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Tank {

    int id;
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    TankClient tc;

    private int x, y;

    private static Random random =  new Random();

    private boolean bL = false, bU = false, bR = false, bD = false;

    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;

    private  int step = random.nextInt(12)+3;

    private boolean good ;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private boolean live = true;

    private int oldX,oldY;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private int life = 100;

    private BloodBar bb = new BloodBar();

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
        this.oldX = x;
        this.oldY = y;
    }

    public Tank(int x, int y, TankClient tc,boolean good) {
        this(x, y,good);
        this.tc = tc;
    }
    public Tank(int x, int y, TankClient tc,Direction direction,boolean good) {
        this(x, y,good);
        this.tc = tc;
        this.dir = direction;
    }

    public void draw(Graphics g) {
        if(!live) {
            if(!good){
                tc.tanks.remove(this);
            }
            return;}
        Color c = g.getColor();
        if(good) g.setColor(Color.red);
        else g.setColor(Color.blue);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.drawString("id: " +id,x,y-10);
        g.setColor(c);

        if (good)
        bb.draw(g);
        switch (ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
                break;
        }

        move();
    }

    public boolean isGood() {
        return good;
    }

    void move() {
        this.oldX = x;
        this.oldY = y;
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

        if (this.dir != Direction.STOP) {
            this.ptDir = this.dir;
        }

        if(x<0) x=0;
        if(y<30) y=30;
        if(x+Tank.WIDTH >TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH -Tank.WIDTH;
        if(y+Tank.HEIGHT >TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT -Tank.HEIGHT;

        if(!good){

            Direction[] directions = Direction.values();
            if(0==step){
                step = random.nextInt(12)+3;
                int rn = random.nextInt(step);
                dir = directions[rn];
            }
            step--;

            if (random.nextInt(40)>38)
                this.fire();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_F2:
                if(!this.live){
                    this.live = true;
                    this.life=100;
                }
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

    void locateDirection() {
        if (bL && !bU && !bR && !bD) dir = Direction.L;
        else if (bL && bU && !bR && !bD) dir = Direction.LU;
        else if (!bL && bU && !bR && !bD) dir = Direction.U;
        else if (!bL && bU && bR && !bD) dir = Direction.RU;
        else if (!bL && !bU && bR && !bD) dir = Direction.R;
        else if (!bL && !bU && bR && bD) dir = Direction.RD;
        else if (!bL && !bU && !bR && bD) dir = Direction.D;
        else if (bL && !bU && !bR && bD) dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
//                tc.m = fire();
                fire();
                break;
            case KeyEvent.VK_A:
                superFire();
                break;
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

    public Missile fire() {
        if (!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y,good, ptDir,tc);
        tc.missileList.add(m);
        return m;
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public boolean collidesWithWall(Wall wall) {
        if(this.live &&this.getRect().intersects(wall.getRect()) ){
//            this.live = false;
//            this.dir = Direction.STOP;
            this.stay();
            return true;
        }
        return  false;
    }

    public boolean collidesWithTanks(java.util.List<Tank> tanks){
        for (int i=0;i<tanks.size();i++){
            Tank t =tanks.get(i);
            if(this!=t){
                if(this.live &&t.isLive() &&this.getRect().intersects(t.getRect())){
                    this.stay();
                    t.stay();;
                    return true;
                }
            }
        }
        return false;
    }

    public Missile fire(Direction dir){
        if (!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y,good, dir,tc);
        tc.missileList.add(m);
        return m;
    }

    private void superFire(){
        Direction[] dirs = Direction.values();
        for (int i=0;i<8;i++){
//            tc.missileList.add(fire(dirs[i]));
            fire(dirs[i]);
        }
    }

    private void stay(){
        x = oldX;
        y = oldY;
    }

    private class BloodBar{
        public void draw(Graphics graphics){
            Color color = graphics.getColor();
            graphics.setColor(color.RED);
            graphics.drawRect(x,y-10,WIDTH,10);
            int w = WIDTH*life/100;
            graphics.fillRect(x,y-10,w,10);
            graphics.setColor(color);
        }
    }

    public boolean eat(Blood b){
        if (this.live && this.getRect().intersects(b.getRect())) {
            this.life = 100;
            return true;
        }
        return false;
    }
}
