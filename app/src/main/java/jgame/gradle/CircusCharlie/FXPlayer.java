/*
Ejemplo original https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 */
package jgame.gradle.CircusCharlie;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum FXPlayer {
   FX00("CircusCharlie/fx00.wav"),
   EVENTO1("CircusCharlie/CircusCharlieAudio1.wav"),
   EVENTO2("CircusCharlie/CircusCharlieAudio2.wav"),
   EVENTO3("CircusCharlie/CircusCharlieAudio3.wav"),
   DERROTA("CircusCharlie/CircusCharlie(derrota).wav"),
   VICTORIA("CircusCharlie/CircusCharlieSonidoVictoria.wav"),
   DRAGONBALL("CircusCharlie/DragonballZ_OST_SonGokuIsTheStrongestAfterAll.wav"),
   DBRAP("CircusCharlie/DBRap.wav"),
   DBSUPER("CircusCharlie/DragonBallSuper.wav"),
   UI("CircusCharlie/UltraInstinto.wav"),
   PB("CircusCharlie/PeakyBlinders.wav"),
   BARDOCK("CircusCharlie/DragonBallZ_Bardock.wav"),
   LEON("CircusCharlie/hola.wav");

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;
   private Clip clip;

   FXPlayer(String wav) {
      try {
         URL url = this.getClass().getClassLoader().getResource("musica/"+wav);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void play() {
      if (volume != Volume.MUTE) {
         if (!clip.isRunning()){
            clip.setFramePosition(0);
            clip.start();
         }
      }
   }

   public void stop(){
      if (volume != Volume.MUTE) {
         if (clip.isRunning()){
            clip.setFramePosition(0);
            clip.stop();
         }
      }
   }

   public void loop(){
      if (volume != Volume.MUTE) {
         if (!clip.isRunning()){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         }
      }
   }

   public void playOnce() {
      if (volume != Volume.MUTE) {
         if (!clip.isRunning()){
            clip.setFramePosition(0);
            clip.start();
            clip.addLineListener(new LineListener() {
               @Override
               public void update(LineEvent event) {
                  if (event.getType() == LineEvent.Type.STOP) {
                     clip.stop();
                     clip.setFramePosition(0);
                     clip.removeLineListener(this);
                  }
               }
            });
         }
      }
   }

   public static void init() {
      values();
   }
}