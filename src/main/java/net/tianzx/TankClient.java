package net.tianzx;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by tianzx on 2016/6/1.
 */
public class TankClient extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank mytank =  new Tank(50,50,this);
    Missile missile ;
    private Image offScreenImage = null;

    public void laughFrame() {
        this.setLocation(GAME_WIDTH/2, GAME_HEIGHT/2);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                System.exit(0);
            }
        });
        setResizable(false);
        setBackground(Color.GREEN);
        this.addKeyListener(new KeyMonitor());
        setVisible(true);

        new Thread(new PaintThread()).start();
    }

    public void paint(Graphics g) {
        if(null!=missile){
            missile.draw(g);
        }
        mytank.draw(g);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        if (null == offScreenImage) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.green);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);

    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.laughFrame();
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            mytank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            mytank.keyReleased(e);
        }
    }
}