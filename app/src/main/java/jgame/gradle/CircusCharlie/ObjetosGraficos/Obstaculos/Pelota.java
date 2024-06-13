package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.Charlie;
import jgame.gradle.CircusCharlie.Vehiculo;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Pelota extends Vehiculo{
    private long invulnerableTime = 0;
    private boolean chocarContraotros = true; // Define si fue montado para que la pelota salga disparada para la izquierda
    private boolean estaMontado = false; // Define si esta montado actualmente en la pelota charlie
    private boolean invulnerable = false;
    private boolean salirDisparada = false;

    public Pelota(String filename, boolean montado) {
        super(filename);
        this.estaMontado = montado;
        try{
            if(images.isEmpty()){
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel3/Pelota2.png")));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel3/Pelota3.png")));
                images.add(imagen);
                imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel3/Pelota4.png")));
                images.add(imagen);
            }
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

    public boolean isCharlieOnTop(Charlie charlie) {
        double charliePosX = charlie.getX();
        double charliePosY = charlie.getY() + charlie.getHeight(); // Asumiendo que `getY()` devuelve la parte superior de Charlie
        // Ajusta los valores según el tamaño de la pelota y de Charlie
        return (charliePosX > this.getX() && charliePosX < (this.getX() + getWidth()) && charliePosY >= this.getY() && charliePosY <= (this.getY() + getHeight()));
    }

    public void leftMax(double valor){
        this.setX(getX() - valor);
    }

    public void jump(){}

    public void update(double delta){
        idx += 0.12;
        indiceImagen = ((int)idx) % images.size();
    }

    public void setEstaMontado (boolean montadoActualmente){
        this.estaMontado = montadoActualmente;
    }

    public boolean getEstaMontado (){
        return estaMontado;
    }

    public void setChocarContraotros (boolean bol){
        this.chocarContraotros = bol;
    }

    public boolean getChocarContraotros (){
        return chocarContraotros;
    }

    public void setInvulnerable(boolean invulnerable, long duration) {
        this.invulnerable = invulnerable;
        if (invulnerable) {
            this.invulnerableTime = System.currentTimeMillis() + duration;
        }
    }

    public boolean isInvulnerable() {
        if (invulnerable && System.currentTimeMillis() > invulnerableTime) {
            invulnerable = false;
        }
        return invulnerable;
    }

    public void setSalirDisparada(boolean salir){
        this.salirDisparada = salir;
    }

    public boolean getSalirDisparada(){
        return this.salirDisparada;
    }
}
