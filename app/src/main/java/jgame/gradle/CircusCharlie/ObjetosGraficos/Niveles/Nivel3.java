package jgame.gradle.CircusCharlie.ObjetosGraficos.Niveles;
import jgame.gradle.CircusCharlie.*;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.DetectorColisiones;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.Pelota;

import java.util.*;
import com.entropyinteractive.Keyboard;
import java.awt.Graphics2D;

public class Nivel3 extends Nivel{
    private static ArrayList<Pelota> listaDePelotas = new ArrayList<>();
    public static Charlie charlie;
    public boolean charlieEnPelota;
    public boolean  puntuacionOtorgada = true;
    private boolean banderaScorePelota;

    public Nivel3(CircusCharlie circusCharlie){
        super(circusCharlie);
        llegoAMeta = false;
        accionEjecutar = false;
        Mundo m = Mundo.getInstance();
        try {
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
            FXPlayer.EVENTO3.loop(); 
            charlie = new Charlie("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga1.png");
            charlie.setImagen("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga1.png");
            charlie.setPISO(430);
            charlie.setPosition(185, charlie.getPISO());
            charlie.quieto();
            fondo = new Fondo("imagenes/JuegoCircusCharlie/ImagenNivel3/FONDO_Nivel3.png", 31);
            cam = new Camara(0, 0);
            cam.setRegionVisible(circusCharlie.getWidth(), 480);
            m.setLimitesMundo(fondo.getWidth(), fondo.getHeight());
            CircusCharlie.setCharlie(charlie);
            circusCharlie.setCamara(cam);
            circusCharlie.setFondo(fondo);
            //Crear las pelotas
            this.crearPelota();
        } catch (Exception e) {
            System.out.println("ERROR 2");
            e.printStackTrace();
        }
    }

    public static boolean llegoAMeta(){
        return llegoAMeta;
    }

    public static void setCharlie(Charlie charlie){
        Nivel3.charlie = charlie;
    }

    private void crearPelota(){
        String imagenPelota = "imagenes/JuegoCircusCharlie/ImagenNivel3/Pelota1.png";
        int numeroAleatorioPosX, cantPelotasContinuas;
        int posXPixel = 185;

        Pelota primerPelota = new Pelota(imagenPelota, true);
        primerPelota.setPosition(posXPixel, 471);
        listaDePelotas.add(primerPelota);
        for (int i = 0; i < 14; i++){
            // Generar un número aleatorio entre 2 y 5
            cantPelotasContinuas = 2 + (int)(Math.random() * ((5 - 2) + 1)); 
            for (int j = 0; j < cantPelotasContinuas; j ++){
                // Generar un número aleatorio entre 350 y 600 para los pixeles
                numeroAleatorioPosX = 350 + (int)(Math.random() * ((600 - 350) + 1));
                posXPixel += numeroAleatorioPosX;
                Pelota pelotita = new Pelota(imagenPelota, false);
                pelotita.setPosition(posXPixel, 471);
                listaDePelotas.add(pelotita);
            }
            numeroAleatorioPosX = 250 + (int)(Math.random() * ((400 - 250) + 1));
            posXPixel += numeroAleatorioPosX;
            Pelota pelotita = new Pelota(imagenPelota, false);
            pelotita.setPosition(posXPixel, 471);
            listaDePelotas.add(pelotita);
        } 
    }

    public void gameDraw(Graphics2D g){
        for (Pelota pelotita : listaDePelotas){
            pelotita.displayObjetos(g);
        }
        charlie.display(g);
    }
    
    public void gameUpdate(double delta, Keyboard keyboard){
        // Metodo que muestra el funcionamiento de las teclas
        super.movimientoTeclas(delta, keyboard);
        double posx = charlie.getX()+(charlie.getWidth()/2);
        double posy = charlie.getY()+charlie.getHeight();
        if(posx > 6464 && charlie.getY() < 420){
            charlie.setPISO(400);
            if(charlie.getY() >= charlie.getPISO()){
                llegoAMeta = true;
                charlie.sumarBonusScore();
                FXPlayer.EVENTO3.stop();
                FXPlayer.VICTORIA.playOnce();
                temporizador.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!accionEjecutar) {
                            CircusCharlie.setNivel(CircusCharlie.getNivel()+1);
                            Nivel1.setCharlie(charlie);
                            CircusCharlie.inicioNivel(false);
                            CircusCharlie.changeState(new Nivel1(circusCharlie));
                            accionEjecutar = true;
                        }
                    }
                }, 4000);
            }
            if(posx < 6525 && posy >= charlie.getPISO()){
                charlie.setX(charlie.getX()+1);
            }else if(posx > 6525 && posy >= charlie.getPISO()){
                charlie.setX(charlie.getX()-1);
            }
        }
        else if(charlie.getX() < 6464 || charlie.getX()> 6586){
            charlie.setPISO(510);
        }
        if(charlie.getY() >= 510){
            cayoAlPiso();
        }
        if(!llegoAMeta){
            // Metodo que hace movimiento de las pelotas y swap de imagen.
            movimientoySwapPelota(delta);
        }
        // Metodo que detecta si Charlie está en la pelota y actualizar estado
        charlieParado(delta);
        // Metodo que si detecta colision entre pelota las expulsa hacia la izquierda
        colisionPelotas();
        // Metodo que cuando la pelota esta pasando el limite de la pantalla izquierda se elimina
        eliminarPelotaDesplazada(charlie);
        // Metodo que hace la animacion de charlie cuando llega a la meta.
        super.animacionMeta(delta);
    }

    public void choqueDelPersonaje(Charlie charlie){
        super.choqueDelPersonaje(charlie);
        FXPlayer.EVENTO3.stop();
    }

    public void reiniciarJuegoXColisiones(double x1, Charlie charlie){
        super.reiniciarJuegoXColisiones(x1, charlie);
        Pelota nuevaPelota = new Pelota("imagenes/JuegoCircusCharlie/ImagenNivel3/Pelota1.png", true);
        nuevaPelota.setPosition(charlie.getX(), 471);
        listaDePelotas.add(nuevaPelota);
    }

    public void reiniciarJuego(double x, Charlie charlie) {
        super.reiniciarJuego(x, charlie);
        FXPlayer.EVENTO3.loop();
    }

    public static Pelota getPelotaEnLaQueEstaParadoCharlie(Charlie charlie) {
        for (Pelota pelotita : listaDePelotas) {
            if (pelotita.isCharlieOnTop(charlie)) {
                return pelotita;
            }
        }
        return null; // Si no está parado en ninguna pelota
    }

    public boolean colisiono() {
        return colisiono;
    }

    // Metodo que detecta los 2 tipos de monos que ya pasaron y los va eliminando
    public void eliminarPelotaDesplazada(Charlie charlie) {
        // Iterar sobre la lista original en sentido inverso para evitar problemas al eliminar elementos
        for (int i = listaDePelotas.size() - 1; i >= 0; i--) {
            Pelota pelotita = listaDePelotas.get(i);
            if (pelotita.getX() <= charlie.getX() - 300) {
                listaDePelotas.remove(i); // Eliminar el Mono Marron de la lista original
            }
        }
    }

    public void movimientoySwapPelota(double delta){
        for (Pelota pelotita : listaDePelotas) {
            if (!pelotita.getEstaMontado() && pelotita.getChocarContraotros()) {
                pelotita.update(delta);
                pelotita.setPosition(pelotita.getX() - 0.9, pelotita.getY());
            }
        }
    }

    public void charlieParado(double delta){
        charlieEnPelota = false;
        for (Pelota pelotita : listaDePelotas) {
            if (DetectorColisiones.detectarCharlieParadoSobrePelota(pelotita, charlie)) {
                charlie.setEnLaPelota(true);
                pelotita.setEstaMontado(true);
                charlie.setPISO(407);
                charlieEnPelota = true;
                if((pelotita.getX()+pelotita.getWidth()/2)>(charlie.getX()+charlie.getWidth()/2)){
                    charlie.setX(charlie.getX()+1);
                }else if((pelotita.getX()+pelotita.getWidth()/2)<(charlie.getX()+charlie.getWidth()/2)){
                    charlie.setX(charlie.getX()-1);
                }
            } else if(charlie.saltando()){
                puntuacionOtorgada = false;
            } else {
                pelotita.setEstaMontado(false);
                banderaScorePelota = false;
            }
        }

        if(!puntuacionOtorgada){
            if(charlie.getY() == charlie.getPISO()){
                Score.sumarScore(100);
                puntuacionOtorgada = true;
                if (!banderaScorePelota) {
                    banderaScorePelota = true;
                    circusCharlie.setTempScore(100, 100, charlie.getY() - 50);
                }
            }
        }
        charlie.setEnLaPelota(charlieEnPelota);
    }

    public void colisionPelotas(){
        for (int i = 0; i < listaDePelotas.size(); i++){
            Pelota pelotita1 = listaDePelotas.get(i);
            for (int j = i + 1; j < listaDePelotas.size(); j++){
                Pelota pelotita2 = listaDePelotas.get(j);
                if(DetectorColisiones.detectarEntrePelotas(pelotita1, pelotita2)){
                    pelotita1.leftMax(10);  // Pelotita1 a la izquierda
                    pelotita2.leftMax(16); // Pelotita2 a la derecha
                }
            }
        }
    }

    public void cayoAlPiso(){
        colisiono = true;
        accion = false;
        restar = false;
        charlie.detenerBonus();
        choqueDelPersonaje(charlie);
    }   
}
