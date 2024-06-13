package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.ObjetoGrafico;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;


public class Money extends ObjetoGrafico{
    private boolean aspiroLaBolsita = false;

    public Money(String filename, boolean aspiro) {
        super(filename);
        this.aspiroLaBolsita = aspiro;
        try{
            if(images.isEmpty()){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                images.add(imagen);
            }
        }catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

    public void setAspiroLaBolsita(boolean seLaAspiro){
        this.aspiroLaBolsita = seLaAspiro;
    }

    public boolean getAspiroLaBolsita(){
        return aspiroLaBolsita;
    }
}
