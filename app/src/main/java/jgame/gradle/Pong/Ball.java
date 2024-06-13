package jgame.gradle.Pong;
import jgame.gradle.CircusCharlie.ObjetoGrafico;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends ObjetoGrafico {
    private final int RADIO = 10;
    private int dx = 5;
    private int dy = 5;

    public Ball(int x, int y){
        this.positionX = x;
        this.positionY = y;
        this.setWidth(RADIO);
    }

    public void mover() {
        this.positionX += dx;
        this.positionY += dy;
    }

    public void rebotarHorizontal() {
        dx = -dx;
    }

    public void rebotarVertical() {
        dy = -dy;
    }

    public void rebotar(){
        dx = -dx;
    }

    public int getRadio(){
        return RADIO;
    }

    public Shape getGrafico(){
        return new Ellipse2D.Double(positionX, positionY, RADIO * 2, RADIO * 2);
    }
}
