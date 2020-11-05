package com.company;

import java.awt.*;

public class BresenhamCircleDrawer implements CircleDrawer {
    private PixelDrawer pd;

    public BresenhamCircleDrawer(PixelDrawer pd) {

        this.pd = pd;
    }

    @Override
    public void drawCircle(int x, int y, int r) {
        Color color = new Color(255, 153, 51);
        int x1 = 0, y1 = r, gap = 0, delta = (2 - 2 * r);
        while (y1 >= 0) {

            pd.colorPixel(x + x1, y + y1, color);
            pd.colorPixel(x + x1, y - y1, color);
            pd.colorPixel(x - x1, y - y1, color);
            pd.colorPixel(x - x1, y + y1, color);
            gap = 2 * (delta + y1) - 1;
            if (delta < 0 && gap <= 0) {
                x1++;
                delta += 2 * x1 + 1;
                continue;
            }
            if (delta > 0 && gap > 0) {
                y1--;
                delta -= 2 * y1 + 1;
                continue;
            }
            x1++;
            delta += 2 * (x1 - y1);
            y1--;
        }
    }


}

