package jgame.gradle.MarioBross;
import javax.swing.ImageIcon;

import jgame.Lanzador.Juego;
import javax.swing.JOptionPane;


public class MarioBross extends Juego{

    public MarioBross(){
        setNombre("Mario Bross");
        setVersion("1.0");
        setDescripcion("Juego de un Fontanero");
        setDesarrolladores("PDF, CJ y SF");
        setImagenPortada(new ImageIcon(this.getClass().getResource("/imagenes/JuegoMarioBross/MarioBross.png")));
        setImplementado(false);
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, "Mario Bross no esta disponible.\n Pero puedes jugar al Circus Charlie", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
