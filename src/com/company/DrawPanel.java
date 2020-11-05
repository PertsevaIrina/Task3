package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);

    private Line axisX = new Line(-1, 0, 1, 0);
    private Line axisY = new Line(0, -1, 0, 1);

    private ArrayList<Line> allLines = new ArrayList<>();
    private Line newLine = null;


    @Override
    public void paint(Graphics g) {
        sc.setScreenH(getHeight());
        sc.setScreenW(getWidth());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        /**/
        drawAll(ld);
        //ld.drawLine(sc.r2s(axisX.getP1()), sc.r2s(axisX.getP2()));
        //ld.drawLine(sc.r2s(axisY.getP1()), sc.r2s(axisY.getP2()));
        /**/
        g.drawImage(bi, 0, 0, null);
    }

    private ScreenPoint prevPoint = null;


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            prevPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        } else if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            newLine = new Line(sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())),
                    sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())));
        }
        repaint();
    }
    private  void drawAll(LineDrawer ld){
        drawLine(ld,axisX);
        drawLine(ld,axisY);
        for (Line q : allLines)
            drawLine(ld,q);
        if (newLine != null)
            drawLine(ld,newLine);
    }
    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            prevPoint = null;
        } else if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            allLines.add(newLine);
            newLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        ScreenPoint currentPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        if (prevPoint != null) {
            ScreenPoint deltaScreen = new ScreenPoint(
                    currentPosition.getX() - prevPoint.getX(),
                    currentPosition.getY() - prevPoint.getY());
            RealPoint deltaReal = sc.s2r(deltaScreen);
            double vectorX = deltaReal.getX() - sc.getCornerX();
            double vectorY = deltaReal.getY() - sc.getCornerY();

            sc.setCornerR(sc.getCornerX() - vectorX);
            sc.setCornerY(sc.getCornerY() - vectorY);
            prevPoint = currentPosition;

        }
        if (newLine != null) {
            newLine.setP2(sc.s2r(currentPosition));
        }
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double step = (clicks > 0) ? 0.99 : 1.01;
        for (int i = Math.abs((clicks)); i > 0; i--) {
            scale *= step;
        }
        sc.setRealW((scale * sc.getRealW()));
        sc.setRealH(scale * sc.getRealH());
        repaint();
    }
}
