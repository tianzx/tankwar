package net.tianzx;

import java.awt.*;

/**
 * Created by tianzx on 2016/6/9.
 */
public class Blood {
    int x,y,w,h;
    TankClient tc;
    int step = 0;

    private int[][] pos = {{350,300},{360,300},{375,275},{400,200},{360,270},{365,290},{340,280}};

    public Blood(){
        x=pos[0][0];
        y=pos[0][1];
        w=h=15;
    }
    public void draw(Graphics g){
        Color color =  g.getColor();
        g.setColor(color.MAGENTA);
        g.fillRect(x,y,w,h);
        g.setColor(color);
        move();
    }

    private void move(){
        step++;
        if(step ==pos.length){
            step = 0;
        }
        x = pos[step][0];
        y = pos[step][1];
    }
}
