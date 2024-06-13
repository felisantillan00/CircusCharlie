package jgame.gradle.CircusCharlie;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class ObjetoGrafico {
	protected BufferedImage imagen;
	protected double positionX = 0;
	protected double positionY = 0;
	protected double height;
	protected double width;
	protected double idx = 0;
	protected int indiceImagen;
	protected ArrayList<BufferedImage> images = new ArrayList<>();

    public ObjetoGrafico(String filename) {
		try {
			imagen = ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
		} catch (IOException e) {
			System.out.println("ZAS! en ObjectoGrafico "+e);
		}
    }

	public ObjetoGrafico(){}

	public void setImagen(BufferedImage imagen){
		this.imagen = imagen;
	}

	public void setImagen(String imagen){
		try {
			this.imagen = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagen));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWidth(){
		return imagen.getWidth();
	}
	
	public int getHeight(){
		return imagen.getHeight();
	}

	public void setHeight(int height){
		this.height = height;
	} 
	public void setWidth(int width){
		this.width = width;
	}

	public void setPosition(double x,double y){
		this.positionX = x;
		this.positionY = y;
	}

	public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

	//No utilizamos este ya que necesitamos para el swap
	public void display(Graphics2D g2) {
		g2.drawImage(imagen,(int) this.positionX,(int) this.positionY,null);
	}
	
	// Este metodo es para los objetos 
	public void displayObjetos(Graphics2D g2){
        if (!images.isEmpty()){
            BufferedImage imagenActual = images.get(indiceImagen);
            if (imagenActual != null){
                g2.drawImage(imagenActual, (int) Math.round(positionX), (int)positionY, null);
            }
        }
	}

	public double getX(){
		return positionX;
	}

	public double getY(){
		return positionY;
	}

	public void setX(double x){
		this.positionX = x;
	}

	public void setY(double y){
		this.positionY = y;
	}
}