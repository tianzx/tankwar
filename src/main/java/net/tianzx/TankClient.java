package net.tianzx;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankClient extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank myTank = new Tank(50, 50, this, Tank.Direction.STOP,true);
//    Tank enemyTank = new Tank(100,100,this,false);
    List<Tank> tanks = new ArrayList<Tank>();
//    Explode explode = new Explode(70,70,this);
    List<Explode> explodes = new ArrayList<Explode>();
//    Missile m = null;
    java.util.List<Missile> missileList = new ArrayList<Missile>();

    Image offScreenImage = null;

    Blood blood = new Blood();
    Wall wall = new Wall(100,200,20,150,this);
    Wall wall2 = new Wall(500,100,300,20,this);

    public void paint(Graphics g) {
//        if (m != null)
        g.drawString("missiles count :"+missileList.size(),10,50);
        g.drawString("explodes count :"+explodes.size(),10,70);
        g.drawString("mytank life :"+myTank.getLife(),10,90);
        for (int i=0;i<missileList.size();i++){
            Missile missile = missileList.get(i);
//            missile.hitTank(enemyTank);
            missile.hitTanks(tanks);
            missile.hitTank(myTank);
            missile.hitWall(wall);
            missile.hitWall(wall2);
            if(!missile.isbLive()){
                missileList.remove(missile);
            }
            missile.draw(g);
        }
        for (int i=0;i<explodes.size();i++){
            Explode explode = explodes.get(i);
            explode.draw(g);
        }
        for (int i=0;i<tanks.size();i++){
            Tank tank = tanks.get(i);
            tank.collidesWithWall(wall);
            tank.collidesWithWall(wall2);
            tank.collidesWithTanks(tanks);
            tank.draw(g);

        }
        myTank.draw(g);
//        enemyTank.draw(g);
        myTank.eat(blood);
        wall.draw(g);
        wall2.draw(g);
        blood.draw(g);
    }

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void lauchFrame() {
        for (int i=0;i<10;i++){
            tanks.add(new Tank(50+40*(i+1),50,this, Tank.Direction.D,false));
        }
        //this.setLocation(400, 300);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.GREEN);

        this.addKeyListener(new KeyMonitor());

        setVisible(true);

        new Thread(new PaintThread()).start();
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.lauchFrame();
    }

    private class PaintThread implements Runnable {

        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

    }

}













