package net.tianzx;

import java.awt.*;

/**
 * Created by tianzx on 2016/6/6.
 */
public class Explode {
    int x, y;
    private boolean live = true;

    int[] diameter = {4, 7, 12, 18, 29, 32, 49, 30, 14, 6};
    int step = 0;
    private TankClient tankClient;

    public Explode(int x, int y, TankClient tankClient) {
        this.x = x;
        this.y = y;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g) {
        if (!live) {
            tankClient.explodes.remove(this);
            return;
        }
            if (diameter.length == step) {
                live = false;
                step = 0;
                return;
            }
            Color color = g.getColor();
            g.setColor(color.ORANGE);
            g.fillOval(x, y, diameter[step], diameter[step]);
            g.setColor(color);
            step++;

        }
    }
