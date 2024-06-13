package jgame.gradle.Pong;
import javax.swing.ImageIcon;
import jgame.Lanzador.Juego;

public class JuegoPong extends Juego{
    public JuegoPong(){
        setNombre("Pong");
        setVersion("1.0");
        setDescripcion("Juego de tenis 2D"); 
        setDesarrolladores("PDF, CJ y SF");
        setImagenPortada(new ImageIcon(this.getClass().getResource("/imagenes/JuegoPong/Pong.png")));
        setImplementado(true);
    }

    @Override
    public void run() {
        new PantallaInicioPong();
    }
}
