package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import jgame.gradle.CircusCharlie.ObjetoGrafico;

public class Aro extends ObjetoGrafico{
    private int indiceImagenActualAroGrande = 0;
    private int indiceImagenActualAroChico = 0;
    private boolean verificarTamano; //True es para saber si es el AroGrande y false si es Aro Chico
    private ArrayList<BufferedImage> imageAroGrandeIzquierdo = new ArrayList<>();
    private ArrayList<BufferedImage> imageAroGrandeDerecho = new ArrayList<>();
    private ArrayList<BufferedImage> imageAroChicoIzquierdo = new ArrayList<>();
    private ArrayList<BufferedImage> imageAroChicoDerecho = new ArrayList<>();
    
    public Aro(String filename, boolean verificarTam){
        super(filename);
        this.verificarTamano = verificarTam;
        try{
            if (imageAroChicoIzquierdo.isEmpty() && this.verificarTamano == true){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                imageAroGrandeIzquierdo.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuego2Izquierda.png")));
                imageAroGrandeIzquierdo.add(imagen);
            }
            if (imageAroGrandeDerecho.isEmpty() && this.verificarTamano == true){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                imageAroGrandeDerecho.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuego2Derecha.png")));
                imageAroGrandeDerecho.add(imagen);
            }
            if (imageAroChicoIzquierdo.isEmpty() && this.verificarTamano == false){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                imageAroChicoIzquierdo.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuegoChico2Izquierdo.png")));
                imageAroChicoIzquierdo.add(imagen);
            }
            if (imageAroChicoDerecho.isEmpty() && this.verificarTamano == false){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                imageAroChicoDerecho.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuegoChico2Derecho.png")));
                imageAroChicoDerecho.add(imagen);
            }
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

    // Dibujar el aro en la posición especificada
    public void displayAroParteIzquierda(Graphics2D g) {
        double posX = getX();
        int posY = (int) getY();
        if (!imageAroGrandeIzquierdo.isEmpty() && this.verificarTamano == true){
            BufferedImage imagenActualAroGrande = imageAroGrandeIzquierdo.get(indiceImagenActualAroGrande);
            if (imagenActualAroGrande != null){
                g.drawImage(imagenActualAroGrande, (int) Math.round(posX), posY, null);
            }
        }
        if (!imageAroChicoIzquierdo.isEmpty() && this.verificarTamano == false){
            BufferedImage imagenActualAroChico = imageAroChicoIzquierdo.get(indiceImagenActualAroChico);
            if (imagenActualAroChico != null){
                g.drawImage(imagenActualAroChico, (int) Math.round(posX), posY, null);
            }
        }
    }

    public void displayAroParteDerecha(Graphics2D g) {
        double posX = getX();
        int posY = (int) getY();
        if (!imageAroGrandeDerecho.isEmpty() && this.verificarTamano == true){
            BufferedImage imagenActualAroGrandeDerecho = imageAroGrandeDerecho.get(indiceImagenActualAroGrande);
            if (imagenActualAroGrandeDerecho != null){
                g.drawImage(imagenActualAroGrandeDerecho, (int) Math.round(posX+40), posY, null);
            }
        }
        if (!imageAroChicoDerecho.isEmpty() && this.verificarTamano == false){
            BufferedImage imagenActualAroChicoDerecho = imageAroChicoDerecho.get(indiceImagenActualAroChico);
            if (imagenActualAroChicoDerecho != null){
                g.drawImage(imagenActualAroChicoDerecho, (int) Math.round(posX+40), posY, null);
            }
        }
    }

    public void update(double delta){
        idx += 0.10;
        if(this.verificarTamano == true){
            indiceImagenActualAroGrande = ((int)idx) % imageAroGrandeIzquierdo.size();
        }
        if(this.verificarTamano == false){
            indiceImagenActualAroChico = ((int)idx) % imageAroChicoIzquierdo.size();
        }
    }

    public boolean getVerificarTamano(){
        return this.verificarTamano;
    }
}