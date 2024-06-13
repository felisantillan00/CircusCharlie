package jgame.gradle.CircusCharlie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class Fondo extends ObjetoGrafico {
	public Fondo(String filename, double posY) {
		super(filename);
		setPosition(0, posY); // El fondo es una imagen estatica, pero muy grande
	}

	public Fondo(int width, int height){
        super();
        this.imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = this.imagen.createGraphics();
        setHeight(height);
        setWidth(width);
        // Rellenar el fondo con color negro
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        // Dibujar la línea blanca en el medio
        g2d.setColor(Color.WHITE);
        g2d.fillRect(width / 2 - 2, 0, 4, height); // Línea de 4 píxeles de ancho
        g2d.dispose();
        g2d.dispose();
    }

    public void displayFondo(Graphics2D g2){
        g2.drawImage(this.imagen, 0, 0, null);
    }
}