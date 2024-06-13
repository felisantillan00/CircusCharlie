package jgame.gradle.CircusCharlie.ObjetosGraficos.Niveles;
import jgame.gradle.CircusCharlie.*;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.Pelota;
import jgame.gradle.Pong.RWproperties;

import java.awt.Graphics2D;
import java.util.*;
import com.entropyinteractive.Keyboard;
import java.awt.event.KeyEvent;

public abstract class Nivel{
    protected CircusCharlie circusCharlie;
    Camara cam;
    Fondo fondo;
    protected static boolean colisiono = false;
    protected static boolean llegoAMeta = false;
    protected static boolean mostrarNivel = false;
    protected static boolean accion = false;
    protected static boolean restar = false;
    protected boolean accionEjecutar;
    protected Timer temporizador = new Timer();
    Date dInit = new Date();
    Date dReloj;
    Date dAhora;
    private int izquieda;
    private int derecha;
    private int salto;
    private String configProp = "configuracionCharlie.properties";
    
    public Nivel(CircusCharlie cc){
        this.circusCharlie = cc;
    }

    public abstract void gameDraw(Graphics2D g);
    public abstract void gameUpdate(double delta, Keyboard keyboard);
    public abstract boolean colisiono();

    public void configTeclas(){
        if(RWproperties.readProperties(configProp, "Movimiento").equals("Flecha izq - Flecha der")){
            this.izquieda = KeyEvent.VK_LEFT;
            this.derecha = KeyEvent.VK_RIGHT;
        }else{
            this.izquieda = KeyEvent.VK_A;
            this.derecha = KeyEvent.VK_D;
        }

        if(RWproperties.readProperties(configProp, "Salto").equals("X")){
            this.salto = KeyEvent.VK_X;
        }else if (RWproperties.readProperties(configProp, "Salto").equals("Flecha arriba")){
            this.salto = KeyEvent.VK_UP;
        }else {
            this.salto = KeyEvent.VK_W;
        }
    }

    public void movimientoTeclas(double delta, Keyboard keyboard){
        configTeclas();
        if(circusCharlie.getNivelActual() instanceof Nivel3){
            Pelota pelotaActual = Nivel3.getPelotaEnLaQueEstaParadoCharlie(circusCharlie.getCharlie());
            if (keyboard.isKeyPressed(izquieda)) {
                if(circusCharlie.getCharlie().getEnLaPelota() && pelotaActual != null){
                    pelotaActual.left();
                    pelotaActual.update(delta);
                }
            }
            if (keyboard.isKeyPressed(derecha)) {
                if(circusCharlie.getCharlie().getEnLaPelota() && pelotaActual != null){
                    pelotaActual.right();
                    pelotaActual.update(delta);
                }
            }
        }
        if (keyboard.isKeyPressed(izquieda)) {
            if (circusCharlie.getCharlie().getX() > 10){
                circusCharlie.getCharlie().left();
                circusCharlie.getCharlie().cambioImagen1();
            }
        }
        if (keyboard.isKeyPressed(derecha)) {
            if ((circusCharlie.getCharlie().getX() + circusCharlie.getCharlie().getWidth() < fondo.getWidth() && !Nivel2.llegoAMeta()) || (circusCharlie.getCharlie().getX() + circusCharlie.getCharlie().getWidth() < fondo.getWidth() && !Nivel3.llegoAMeta())) {
                circusCharlie.getCharlie().right();
                circusCharlie.getCharlie().cambioImagen1();
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            circusCharlie.getCharlie().setPosition(5700, circusCharlie.getCharlie().getY());
        }
        // check the list of key events for a pressed escape key
        LinkedList<KeyEvent> keyEvents = keyboard.getEvents();
        for (KeyEvent event : keyEvents) {
            if ((event.getID() == KeyEvent.KEY_RELEASED)) {
                circusCharlie.getCharlie().quieto();
            }
            if ((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == salto)) {
                if (Nivel3.llegoAMeta() == false || Nivel2.llegoAMeta() == false) {
                    circusCharlie.getCharlie().jump();
                    FXPlayer.FX00.play();
                }
            }
            if ((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                FXPlayer.EVENTO1.stop();
                circusCharlie.stop();
            }
            
        }
        circusCharlie.getCharlie().update(delta);
        cam.seguirPersonaje(circusCharlie.getCharlie(),180); /// la camara sigue al Personaje
    }

    public void movimientoTeclas(double delta, Keyboard keyboard, Leon leon){
        configTeclas();
        if (keyboard.isKeyPressed(izquieda)) {
            if (leon.getX() > 10 && Nivel1.llegoAMeta() == false) {
                circusCharlie.getCharlie().left();
                leon.left();
                if(!circusCharlie.getCharlie().saltando()){
                    circusCharlie.getCharlie().cambioImagen();
                }
            }
        }
        if (keyboard.isKeyPressed(derecha)) {
            if (leon.getX() + leon.getWidth() < fondo.getWidth() && Nivel1.llegoAMeta() == false) {
                circusCharlie.getCharlie().right();
                leon.right();
                if(!circusCharlie.getCharlie().saltando()){
                    circusCharlie.getCharlie().cambioImagen();
                }
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            circusCharlie.getCharlie().setPosition(7400 + 174, circusCharlie.getCharlie().getY());
            leon.setPosition(7400 + 143, leon.getY());
        }
        // check the list of key events for a pressed escape key
        LinkedList<KeyEvent> keyEvents = keyboard.getEvents();
        for (KeyEvent event : keyEvents) {
            if ((event.getID() == KeyEvent.KEY_RELEASED)) {
                circusCharlie.getCharlie().quieto();
                leon.quieto();
            }
            if ((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == salto)) {
                if (Nivel1.llegoAMeta() == false) {
                    circusCharlie.getCharlie().jump();
                    leon.jump();
                    FXPlayer.FX00.play();
                }
            }
            if ((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                FXPlayer.EVENTO1.stop();
                circusCharlie.stop();
            }
        }
        leon.update(delta);
        circusCharlie.getCharlie().update(delta);
        cam.seguirPersonaje(circusCharlie.getCharlie(), leon, 143); /// la camara sigue al Leon
    }

    public void animacionMeta(double delta){
        if (Nivel1.llegoAMeta() || Nivel2.llegoAMeta() || Nivel3.llegoAMeta()) {
            if (dReloj == null) {
                dReloj = new Date();
            }
            dAhora = new Date();
            circusCharlie.getCharlie().updateLlegadaMeta(delta);
        }
    }
    
    protected void choqueDelPersonaje(Charlie charlie) {
        FXPlayer.DERROTA.playOnce();
        charlie.setPISO(charlie.getY());
        charlie.setPosition(charlie.getX(), charlie.getPISO());
        charlie.setImagen("imagenes/JuegoCircusCharlie/Generales/charlieDerrota.png");
        Timer tempo = new Timer();
        tempo.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!accion) {
                    if(!restar){
                        charlie.restarVida(1);
                        restar = true;
                    }
                    reiniciarJuegoXColisiones(charlie.getX(), charlie);
                    accion = true;
                }
            }
        }, 4000);
    }

    // Cuando detecta una colision, reiniciamos el juego en ese punto
    protected void reiniciarJuegoXColisiones(double x1, Charlie charlie) {
        // Busca el checkpoint más cercano a la posición x
        int[] checkpointsEjeX = { 201, 990, 1814, 2654, 3451, 4259, 5066, 5869, 6668, 7433 };
        int pos = 0, i;
        for (i = 1; i < checkpointsEjeX.length; i++) {
            if (checkpointsEjeX[i] < x1) {
                pos = i - 1;
            }
        }
        // Reinicia el juego en el checkpoint más cercano
        int newX = checkpointsEjeX[pos];
        mostrarNivel = true;
        CircusCharlie.inicioNivel(false);
        reiniciarJuego(newX, charlie);
    }

    // Método para reiniciar el juego en una posición específica
    protected void reiniciarJuego(double x, Charlie charlie) {
        charlie.setPISO(412);
        charlie.setPosition(x + 31, charlie.getPISO());
        llegoAMeta = false;
        colisiono = false;
        FXPlayer.DERROTA.stop();
        charlie.setImagen("imagenes/JuegoCircusCharlie/Generales/charlie.png");
        charlie.reiniciarDescuento();
    }
}