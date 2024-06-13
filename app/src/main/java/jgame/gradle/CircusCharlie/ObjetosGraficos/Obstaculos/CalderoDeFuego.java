package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.ObjetoGrafico;
import java.io.IOException;
import javax.imageio.*;
import java.util.*;

public class CalderoDeFuego extends ObjetoGrafico {
    public CalderoDeFuego(String filename) {
        super(filename);
        try {
            if(images.isEmpty()){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel1/fuego2.png")));
                images.add(imagen);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

    public void update(double delta){
        idx += 0.165;
        indiceImagen = ((int)idx) % images.size();
    }
}