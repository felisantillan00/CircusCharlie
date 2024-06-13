package jgame.gradle.CircusCharlie.ObjetosGraficos.Niveles;

import jgame.gradle.CircusCharlie.*;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.MonoMarron;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.DetectorColisiones;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.MonoAzul;

import java.util.*;

import com.entropyinteractive.Keyboard;
import java.awt.Graphics2D;

public class Nivel2 extends Nivel{
    private ArrayList<MonoMarron> listaDeMonosMarron = new ArrayList<>();
    private ArrayList<MonoAzul> listaDeMonosAzul = new ArrayList<>();
    private boolean mostrarMonos = true;
    private boolean pasoXMonoMarron = false;
    private boolean pasoXMonoAzul = false;
    public static Charlie charlie;
    private boolean banderaScoreMA;
    private boolean banderaScoreMM;

    public Nivel2(CircusCharlie circusCharlie){
        super(circusCharlie);
        llegoAMeta = false;
        accionEjecutar = false;
        Mundo m = Mundo.getInstance();
        try {
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
            FXPlayer.EVENTO2.loop(); 
            charlie = new Charlie("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga1.png");
            charlie.setImagen("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga1.png");
            charlie.setPISO(220);
            charlie.setPosition(174, charlie.getPISO());
            fondo = new Fondo("imagenes/JuegoCircusCharlie/ImagenNivel2/FONDO_Nivel2.png", 31);
            cam = new Camara(0, -26);
            cam.setRegionVisible(circusCharlie.getWidth(), 480);
            m.setLimitesMundo(fondo.getWidth(), fondo.getHeight());
            CircusCharlie.setCharlie(charlie);
            circusCharlie.setFondo(fondo);
            circusCharlie.setCamara(cam);
            //Crear los Monos
            this.crearMonos();
        } catch (Exception e) {
            System.out.println("ERROR 1");
            e.printStackTrace();
        }
    }

    public static void setCharlie(Charlie charlie){
        Nivel2.charlie = charlie;
    }

    public static boolean llegoAMeta(){
        return llegoAMeta;
    }

    @Override
    public boolean colisiono() {
        return colisiono;
    }
    
    public void gameUpdate(double delta, Keyboard keyboard){
        // Metodo que muestra el funcionamiento de las teclas
        super.movimientoTeclas(delta, keyboard);
        // Posicionamiento en el podio
        double posx = charlie.getX() + (charlie.getWidth()/2);
        double posy = charlie.getY() + charlie.getHeight();
        if(posx > 5550 && charlie.getY() < 174){
            charlie.setPISO(150);
            mostrarMonos = false;
            if(charlie.getY() >= charlie.getPISO()){
                llegoAMeta = true;
                charlie.sumarBonusScore();
                FXPlayer.EVENTO2.stop();
                FXPlayer.VICTORIA.playOnce();
                temporizador.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!accionEjecutar) {
                            CircusCharlie.setNivel(CircusCharlie.getNivel()+1);
                            Nivel3.setCharlie(charlie);
                            CircusCharlie.inicioNivel(false);
                            CircusCharlie.changeState(new Nivel3(circusCharlie));
                            accionEjecutar = true;
                            llegoAMeta = false;
                        }
                    }
                }, 4000);
            }
            if(posx < 5650 && posy >= charlie.getPISO()){
                charlie.setX(charlie.getX()+1);
            }else if(posx>5650 && posy >= charlie.getPISO()){
                charlie.setX(charlie.getX()-1);
            }
        }
        else if(charlie.getX()<5550 || charlie.getX()>5743){
            charlie.setPISO(220);
            mostrarMonos = true;
        }
        if(charlie.getY() + charlie.getHeight() > charlie.getPISO()){
            banderaScoreMA = false;
            banderaScoreMM = false;
        }
        if(!llegoAMeta){
            // Metodo que le da movimiento a ambos monos y detecta colisiones de los entre charlie y monos
            movimientoMonoYColision(delta);
        }
        // Método que suma los puntos al saltar los monos
        sumarPuntosPorObstaculo();
        // Metodo que detecta la colision entre monos
        colisionEntreMonos();
        // Metodo que detecta si cualquiera de los 2 monos lo paso a charlie
        eliminarMonosDesplazados(charlie);
        // Metodo para cuando Charlie llego a la meta
        super.animacionMeta(delta);
    }

    public void gameDraw(Graphics2D g){
        if(!mostrarNivel){
            if(mostrarMonos){
                for (MonoMarron monitoMarron: listaDeMonosMarron){
                    monitoMarron.displayObjetos(g);
                }
                for (MonoAzul monitoAzul: listaDeMonosAzul){
                    monitoAzul.displayObjetos(g);
                }
            }
            charlie.display(g);
        }else{
            charlie.imagenNivel();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mostrarNivel=false;
                }
            }, 3000);
        }
    }

    public void crearMonos(){
        String imagenMonoMarron = "imagenes/JuegoCircusCharlie/ImagenNivel2/Mono1.png";
        String imagenMonoAzul = "imagenes/JuegoCircusCharlie/ImagenNivel2/monoPolenta1.png";
        int numeroAleatorioPosX, cantMonosNormalesContinuos;
        int posXPixel = 850;
        MonoMarron primerMonito = new MonoMarron(imagenMonoMarron);
        primerMonito.setPosition(posXPixel, 240);
        listaDeMonosMarron.add(primerMonito);
        for (int i = 0; i < 14; i++){
            // Generar un número aleatorio entre 2 y 5
            cantMonosNormalesContinuos = 2 + (int)(Math.random() * ((5 - 2) + 1)); 
            for (int j = 0; j < cantMonosNormalesContinuos; j ++){
                // Generar un número aleatorio entre 350 y 600 para los pixeles
                numeroAleatorioPosX = 350 + (int)(Math.random() * ((600 - 350) + 1));
                posXPixel += numeroAleatorioPosX;
                MonoMarron monitoMarron = new MonoMarron(imagenMonoMarron);
                monitoMarron.setPosition(posXPixel, 240);
                listaDeMonosMarron.add(monitoMarron);
            }
            numeroAleatorioPosX = 250 + (int)(Math.random() * ((400 - 250) + 1));
            posXPixel += numeroAleatorioPosX;
            MonoAzul monitoAzul = new MonoAzul(imagenMonoAzul);
            monitoAzul.setPosition(posXPixel, 240);
            listaDeMonosAzul.add(monitoAzul);
        }        
    }

    public void choqueDelPersonaje(Charlie charlie){
        super.choqueDelPersonaje(charlie);
        FXPlayer.EVENTO2.stop();
    }

    public void reiniciarJuego(double x, Charlie charlie) {
        super.reiniciarJuego(x, charlie);
        FXPlayer.EVENTO2.loop();
    }

    // Metodo que detecta los 2 tipos de monos que ya pasaron y los va eliminando
    public void eliminarMonosDesplazados(Charlie charlie) {
        // Iterar sobre la lista original en sentido inverso para evitar problemas al eliminar elementos
        for (int i = listaDeMonosMarron.size() - 1; i >= 0; i--) {
            MonoMarron mM = listaDeMonosMarron.get(i);
            if (mM.getX() <= charlie.getX() - 180) {
                listaDeMonosMarron.remove(i); // Eliminar el Mono Marron de la lista original
            }
        }
        for(int i = listaDeMonosAzul.size() - 1; i >= 0; i--){
            MonoAzul mA = listaDeMonosAzul.get(i);
            if (mA.getX() <= charlie.getX() - 180){
                listaDeMonosAzul.remove(i); // Eliminar el Mono Azul de la lista original
            }
        }
    }

    public void movimientoMonoYColision(double delta){
        if(!colisiono){          
            for (MonoAzul mA : listaDeMonosAzul){
                mA.update(delta);
                mA.setPosition(mA.getX() - 5.0, mA.getY());
                if(DetectorColisiones.detectarMonoAzul(mA, charlie)){
                    colisiono = true;
                    accion = false;
                    restar = false;
                    charlie.detenerBonus();
                    choqueDelPersonaje(charlie);
                }else if(DetectorColisiones.detectarArribaMonoAzul(mA, charlie)){
                    pasoXMonoAzul = true;
                    if (!banderaScoreMA) {
                        banderaScoreMA = true;
                        circusCharlie.setTempScore(100, 100, 150);
                    }
                }
            }
            for (MonoMarron mM : listaDeMonosMarron){
                if(!mM.getIsStopped()){
                    mM.update(delta);
                    mM.setPosition(mM.getX() - 1.5, mM.getY());
                }
                if(DetectorColisiones.detectarMonoNormal(mM, charlie)){
                    colisiono = true;
                    accion = false;
                    restar = false;
                    charlie.detenerBonus();
                    choqueDelPersonaje(charlie);
                }else if(DetectorColisiones.detectarArribaMonoMarron(mM, charlie)){
                    pasoXMonoAzul = true;
                    if (!banderaScoreMM) {
                        banderaScoreMM = true;
                        circusCharlie.setTempScore(100, 100, 150);
                    }
                }
            }
        }
    }

    public void sumarPuntosPorObstaculo(){
        if((pasoXMonoMarron && !pasoXMonoAzul)||(!pasoXMonoMarron && pasoXMonoAzul)){
            if(charlie.getY() == charlie.getPISO()){
                Score.sumarScore(100);
                pasoXMonoAzul = false;
                pasoXMonoMarron = false;
            }else if(pasoXMonoMarron && pasoXMonoAzul){
                if(charlie.getY() == charlie.getPISO()){
                    Score.sumarScore(500);
                    pasoXMonoMarron = false;
                    pasoXMonoAzul = false;
                }
            }
        }
    }
    
    public void colisionEntreMonos(){
        for(MonoMarron mM : listaDeMonosMarron){
            for(MonoAzul mA: listaDeMonosAzul){
                if(DetectorColisiones.detectarEntreMonos(mM, mA)){
                    mA.saltoMonoAZul();
                    mM.detenerMono();
                }
            }
        }
    }
}
