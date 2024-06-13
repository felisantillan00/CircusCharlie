package jgame.gradle.Pong;

import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
    public static Clip clip;

    public static void iniciar(String nombre){
        try {
            //app/src/main/resources/musica/Pong/muchachos.wav
            //File file = new File("app/src/main/resources/musica/Pong/" + nombre + ".wav");
            InputStream inputStream = Sonido.class.getResourceAsStream("/musica/Pong/" + nombre + ".wav");

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedInputStream);
            
            clip = AudioSystem.getClip();

            clip.open(audioInput);

            clip.start();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void parar(){
        clip.stop();
    }

    public static void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void cambiarCancion(String ruta){
        parar();
        iniciar(ruta);
        loop();
    }
}
