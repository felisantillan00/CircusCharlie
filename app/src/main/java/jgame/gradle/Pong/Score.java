package jgame.gradle.Pong;
import jgame.gradle.CircusCharlie.*;
import java.awt.*;

public class Score extends ObjetoGrafico{
    private int player;
    private int puntos = 0;

    public Score(int player, int posX){
        this.player = player;
        this.positionX = posX;
    }

    public int getPlayer() {
        return this.player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos =+ puntos;
    }

    public void draw(Graphics2D g, jgame.gradle.CircusCharlie.Fondo fondo) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        g.drawLine(fondo.getWidth() / 2, 0, fondo.getWidth() / 2, fondo.getHeight());
        g.drawString(String.valueOf(puntos / 10) + String.valueOf(puntos % 10), (int) positionX, 80);
    }

    public void mostrarMensaje(Graphics g, String ganador, Fondo fondo) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = g.getFontMetrics();
        int x = (fondo.getWidth() - metrics.stringWidth(ganador)) / 2;
        int y = fondo.getHeight() / 2;
        g.drawString(ganador, x, y);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = g.getFontMetrics();
        int xx = (fondo.getWidth() - metrics2.stringWidth("Para reiniciar presione Enter")) / 2;
        int yy = fondo.getHeight() / 2;
        g.drawString("Para reiniciar presione Enter", xx, yy + 30);
    }
}