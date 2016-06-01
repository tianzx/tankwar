package net.tianzx;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by tianzx on 2016/6/1.
 */
 public class TankClient extends Frame{

    private int x = 50;
    private int y = 50;

    public void laughFrame(){
        this.setLocation(400,300);
        this.setSize(800,600);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                System.exit(0);
            }
        });
        setResizable(false);
        setBackground(Color.GREEN);
        setVisible(true);

        new Thread(new PaintThread()).start();
    }

    public void paint(Graphics g){
        Color c =g.getColor();
        g.setColor(Color.red);
        g.fillOval(x,y,30,30);
        g.setColor(c);
        y+=5;
    }
    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.laughFrame();
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (true){
                repaint();
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}