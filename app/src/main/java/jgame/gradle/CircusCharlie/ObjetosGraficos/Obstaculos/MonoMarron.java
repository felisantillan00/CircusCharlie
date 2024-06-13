package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.ObjetoGrafico;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;


public class MonoMarron extends ObjetoGrafico {
    private double velocityX;
    private boolean isStopped;
    private final int stopDuration = 1000; // Duración en milisegundos que el mono se detiene

    public MonoMarron(String filename) {
        super(filename);
        try{
            if(images.isEmpty()){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/mono2.png")));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/mono3.png")));
                images.add(imagen);
            }
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }
    
    public boolean getIsStopped(){
        return isStopped;
    }

    public void update(double delta) {
        // Solo actualizar la posición si no está detenido
        if (!isStopped) {
            this.positionX += velocityX * delta;
            idx += 0.04;
            indiceImagen = ((int) idx) % images.size();
        }
    }

    public void detenerMono() {
        if (!isStopped) {
            isStopped = true;
            velocityX = 0; // Detén el movimiento en X al detenerse
            // Programa la reanudación del movimiento después de stopDuration milisegundos
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isStopped = false;
                    velocityX = -2; // Restablece la velocidad en X después de detenerse
                }
            }, stopDuration);
        }
    }
}
