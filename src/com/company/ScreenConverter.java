package com.company;

public class ScreenConverter {
    private double cornerR, cornerY, realW, realH;
    private int screenW, screenH;

    public ScreenConverter(double xR, double yR, double wR, double hR, int wS, int hS) {
        this.cornerR = xR;
        this.cornerY = yR;
        this.realW = wR;
        this.realH = hR;
        this.screenW = wS;
        this.screenH = hS;
    }

    public ScreenPoint r2s(RealPoint p) {
        int x = (int)((p.getX() - cornerR) * screenW / realW);
        int y = (int)((cornerY - p.getY()) * screenH / realH);
        return new ScreenPoint(x, y);
    }

    public RealPoint s2r(ScreenPoint p) {
        double x = p.getX() * realW / screenW + cornerR;
        double y = cornerY - p.getY() * realH / screenH;
        return new RealPoint(x, y);
    }

    public double getCornerX() {
        return cornerR;
    }

    public void setCornerR(double cornerR) {
        this.cornerR = cornerR;
    }

    public double getCornerY() {
        return cornerY;
    }

    public void setCornerY(double cornerY) {
        this.cornerY = cornerY;
    }

    public double getRealW() {
        return realW;
    }

    public void setRealW(double realW) {
        this.realW = realW;
    }

    public double getRealH() {
        return realH;
    }

    public void setRealH(double realH) {
        this.realH = realH;
    }

    public int getScreenW() {
        return screenW;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }
}
