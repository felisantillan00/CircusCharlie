package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.ObjetoGrafico;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class MonoAzul extends ObjetoGrafico{
    private double velocityY = 20; 
    private double gravity = 0.5;
    private boolean onGround = true;
    private boolean isJumping = false;

    public MonoAzul(String filename) {
        super(filename);
        try{
            if(images.isEmpty()){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/monoPolenta2.png")));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/monoPolenta3.png")));
                images.add(imagen);
            }
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

    // Parte que nose si anda de blackbox
    public void startJump() {
        if (onGround) {
            velocityY = -10;
            onGround = false;
            isJumping = true;
        }
    }

    public void endJump() {
        if (velocityY < -14) {
            velocityY = -14;
        }
    }

    public void update(double delta) {
        int posY = (int) this.getY();
        if (isJumping) { // Limitar el tiempo de salto a 200ms
            endJump();
            isJumping = false;
        }
        velocityY += gravity;
        setY(posY + velocityY);
        if (posY > 240.0) { // Cambiar la posición de suelo a 240
            setY(240);
            velocityY = 0.0;
            onGround = true;
        }
        idx += 0.04;
        indiceImagen = ((int) idx) % images.size();
    }

    public void saltoMonoAZul() {
        startJump();
        isJumping = true;
    }
}
