package jgame.gradle.CircusCharlie.ObjetosGraficos.Niveles;

import java.awt.Graphics2D;
import java.util.*;
import com.entropyinteractive.Keyboard;
import jgame.gradle.CircusCharlie.*;
import jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos.*;

public class Nivel1 extends Nivel {
    private ArrayList<Aro> listaDeArosIzquierdo = new ArrayList<>();
    private ArrayList<Aro> listaDeArosDerecho = new ArrayList<>();
    private ArrayList<CalderoDeFuego> listaDeCalderos = new ArrayList<>();
    private ArrayList<Money> listaDeBolsaDeMoneda = new ArrayList<>();
    private static boolean banderaScoreAro = false;
    private boolean banderaScoreCaldero = false;
    private boolean mostrarArosYMoney = true;
    private boolean pasoXAro = false;
    private boolean pasoXCaldero = false;
    public static Charlie charlie;
    public static Leon leon;

    public Nivel1(CircusCharlie circusCharlie) {
        super(circusCharlie);
        llegoAMeta = false;
        accionEjecutar = false;
        Mundo m = Mundo.getInstance();
        try {
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
            FXPlayer.EVENTO1.loop();
            if(charlie == null){
                charlie = new Charlie("imagenes/JuegoCircusCharlie/Generales/charlie.png");
                leon = new Leon("imagenes/JuegoCircusCharlie/ImagenNivel1/leon.png");
            }
            charlie.setImagen("imagenes/JuegoCircusCharlie/Generales/charlie.png");
            leon.setImagen("imagenes/JuegoCircusCharlie/ImagenNivel1/leon.png");
            charlie.setPISO(412);
            charlie.setPosition(174, charlie.getPISO());
            charlie.quieto();
            leon.setPISO(477);
            leon.setPosition(143, leon.getPISO());
            leon.quieto();
            fondo = new Fondo("imagenes/JuegoCircusCharlie/ImagenNivel1/FONDO.png", 31);
            cam = new Camara(0,0);
            cam.setRegionVisible(circusCharlie.getWidth(), 480);
            m.setLimitesMundo(fondo.getWidth(), fondo.getHeight());
            CircusCharlie.setCharlie(charlie);
            circusCharlie.setCamara(cam);
            circusCharlie.setFondo(fondo);
            // Crear los aros
            this.crearAros();
            // Crear los calderos
            this.crearCalderos();
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public static void setCharlie(Charlie charlie){
        Nivel1.charlie = charlie;
    }

    public static boolean llegoAMeta() {
        return llegoAMeta;
    }

    public boolean colisiono() {
        return colisiono;
    }

    public void gameUpdate(double delta, Keyboard keyboard) {
        // Metodo que muestra el funcionamiento de las teclas
        super.movimientoTeclas(delta, keyboard, leon);
        double posx = leon.getX() + (leon.getWidth() / 2);
        double posy = leon.getY() + leon.getHeight();
        if (posx > 8060 && leon.getY() < 417) {
            leon.setPISO(407);
            charlie.setPISO(343);
            mostrarArosYMoney = false;
            if (leon.getY() >= leon.getPISO()) {
                llegoAMeta = true;
                charlie.sumarBonusScore();
                FXPlayer.EVENTO1.stop();
                FXPlayer.VICTORIA.playOnce();
                temporizador.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!accionEjecutar) {
                            CircusCharlie.setNivel(CircusCharlie.getNivel()+1);
                            Nivel2.setCharlie(charlie);
                            CircusCharlie.inicioNivel(false);
                            CircusCharlie.changeState(new Nivel2(circusCharlie));
                            accionEjecutar = true;
                            llegoAMeta = false;
                        }
                    }
                }, 4000);
            }
            if (posx < 8124 && posy >= leon.getPISO()) {
                leon.setX(leon.getX() + 1);
                charlie.setX(charlie.getX() + 1);
            } else if (posx > 8124 && posy >= leon.getPISO()) {
                leon.setX(leon.getX() - 1);
                charlie.setX(charlie.getX() - 1);
            }
        } else if (leon.getX() < 8060 || leon.getX() > 8188) {
            charlie.setPISO(413);
            leon.setPISO(477);
            mostrarArosYMoney = true;
        }
        if (leon.getY() + leon.getHeight() > leon.getPISO()) {
            banderaScoreCaldero = false;
            banderaScoreAro = false;
        }
        // Metodo que hace swap de imagenes de cada caldero
        swapCaldero(delta);
        // Metodo que elimina los aros desplazados cuando pasan atras de charlie.
        eliminarArosDesplazados();
        // Seccion de colisiones
        if(!llegoAMeta){
            // Metodo que da movimiento a la bolsita de money + suma los respectivos puntos
            movimientoYSumaBolsita();
            // Metodo que detecta si charlie choco contra un aro o si paso por en medio para sumar puntos.
            movimientoYcolisionConElAro(delta);
        }
        // Metodo que detecta el caldero si colisiono o lo salto para sumar su respectivo puntaje
        colisionConElCaldero();
        // Metodo para detectar ciertos puntajes respectivos junto el aro con el caldero
        sumarPuntosPorObstaculo();
        // Metodo que elimina los aros desplazados cuando toca el limite de charlie
        eliminarArosDesplazados();
        // Metodo para cuando Charlie llego a la meta
        super.animacionMeta(delta);
    }
    
    public void gameDraw(Graphics2D g) {
        // Dibujar los calderos
        if (!mostrarNivel) {
            for (CalderoDeFuego calderito : listaDeCalderos) {
                calderito.displayObjetos(g);
            }
            // Dibujar los aros
            if(mostrarArosYMoney){
                for (Aro aroDerecha : listaDeArosIzquierdo) {
                    aroDerecha.displayAroParteIzquierda(g);
                }
            }
            leon.display(g);
            charlie.display(g);
            if(mostrarArosYMoney){
                for(Money bolsita : listaDeBolsaDeMoneda){
                    bolsita.display(g);
                }
                for (Aro aroIzquierda : listaDeArosDerecho) {
                    aroIzquierda.displayAroParteDerecha(g);
                }
            }
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
    
    // Metodo que detecta los aros y calderos que ya pasaron y los va eliminando
    public void eliminarArosDesplazados() {
        // Iterar sobre la lista original en sentido inverso para evitar problemas al
        // eliminar elementos
        for (int i = listaDeArosIzquierdo.size() - 1; i >= 0; i--) {
            Aro aro = listaDeArosIzquierdo.get(i);
            if (aro.getX() <= leon.getX() - 145) {
                listaDeArosIzquierdo.remove(i); // Eliminar el aro de la lista original
                listaDeArosDerecho.remove(i); // Eliminar el aro de la lista original
            }
        }
        for(int i = listaDeBolsaDeMoneda.size() - 1; i >= 0; i--){
            Money bolsita = listaDeBolsaDeMoneda.get(i);
            if (bolsita.getX() <= leon.getX() - 136 || bolsita.getAspiroLaBolsita()){
                listaDeBolsaDeMoneda.remove(i); // Eliminar la bolsa de moneda de la lista original
            }
        }
    }

    public void choqueDelPersonaje(Charlie charlie) {
        super.choqueDelPersonaje(charlie);
        FXPlayer.EVENTO1.stop();
        leon.setPISO(leon.getY());
        leon.setPosition(leon.getX(), leon.getPISO());
        leon.setImagen("imagenes/JuegoCircusCharlie/ImagenNivel1/leonDerrota.png");
    }

    // Método para reiniciar el juego en una posición específica
    public void reiniciarJuego(double x, Charlie charlie) {
        super.reiniciarJuego(x, charlie);
        FXPlayer.EVENTO1.loop();
        leon.setPISO(477);
        leon.setPosition(x, leon.getPISO());
        leon.setImagen("imagenes/JuegoCircusCharlie/ImagenNivel1/leon.png");
    }

    // Metodo para crear aros
    public void crearAros() {
        String imagenAroGrandeIzquierda = "imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuego1Izquierda.png";
        String imagenAroGrandeDerecha = "imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuego1Derecha.png";
        String imagenAroChicoIzquierdo = "imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuegoChico1Izquierdo.png";
        String imagenAroChicoDerecho = "imagenes/JuegoCircusCharlie/ImagenNivel1/aroDeFuegoChico1Derecho.png";
        String imagenBolsitaDeMoneda = "imagenes/JuegoCircusCharlie/ImagenNivel1/money.png";
        int posXPixel = 850;
        Aro primerAroIzquierda = new Aro(imagenAroGrandeIzquierda, true);
        primerAroIzquierda.setPosition(posXPixel, 217);
        listaDeArosIzquierdo.add(primerAroIzquierda);
        Aro primerAroDerecha = new Aro(imagenAroGrandeDerecha, true);
        primerAroDerecha.setPosition(posXPixel, 217);
        listaDeArosDerecho.add(primerAroDerecha);
        for (int i = 0; i < 20; i++) {
            // Generar un número aleatorio entre 2 y 5
            int numeroAleatorio2 = 2 + (int) (Math.random() * ((5 - 2) + 1));
            for (int j = 0; j < numeroAleatorio2; j++) {
                // Generar un número aleatorio entre 350 y 600 para los pixeles
                int numeroAleatorio1 = 350 + (int) (Math.random() * ((600 - 350) + 1));
                posXPixel += numeroAleatorio1;
                Aro aroGrandeIzquierda = new Aro(imagenAroGrandeIzquierda, true);
                aroGrandeIzquierda.setPosition(posXPixel, 217);
                listaDeArosIzquierdo.add(aroGrandeIzquierda);
                Aro aroGrandeDerecha = new Aro(imagenAroGrandeDerecha, true);
                aroGrandeDerecha.setPosition(posXPixel, 217);
                listaDeArosDerecho.add(aroGrandeDerecha);
            }
            int numeroAleatorio1 = 250 + (int) (Math.random() * ((400 - 250) + 1));
            posXPixel += numeroAleatorio1;
            Aro aroChicoIzquierdo = new Aro(imagenAroChicoIzquierdo, false);
            aroChicoIzquierdo.setPosition(posXPixel, 217);
            listaDeArosIzquierdo.add(aroChicoIzquierdo);
            Aro aroChicoDerecho = new Aro(imagenAroChicoDerecho, false);
            aroChicoDerecho.setPosition(posXPixel, 217);
            listaDeArosDerecho.add(aroChicoDerecho);
            Money bolsita = new Money(imagenBolsitaDeMoneda, false);
            bolsita.setPosition(posXPixel + 7, 260);
            listaDeBolsaDeMoneda.add(bolsita);
        }
    }

    // Metodo para crear calderos
    public void crearCalderos() {
        String imagen = "imagenes/JuegoCircusCharlie/ImagenNivel1/fuego1.png";
        int[] posicionesX = {1550, 2390, 3180, 3990, 4795, 5600, 6400, 7160, 7970 };
        int posY = 435;
        for (int posX : posicionesX) {
            CalderoDeFuego caldero = new CalderoDeFuego(imagen);
            caldero.setPosition(posX, posY);
            listaDeCalderos.add(caldero);
        }
    }

    public void movimientoYcolisionConElAro(double delta){
        for (Aro aro : listaDeArosIzquierdo) {
            aro.update(delta);
            aro.setPosition(aro.getX() - 1.3, aro.getY());
            if (DetectorColisiones.detectarAro(aro, charlie)) {
                colisiono = true;
                accion = false;
                restar=false;
                charlie.detenerBonus();
                choqueDelPersonaje(charlie);
            } else if (DetectorColisiones.detectarMedioAro(aro, charlie)) {
                pasoXAro = true;
                if (!banderaScoreAro) {
                    banderaScoreAro = true;
                    circusCharlie.setTempScore(100, 180, 250);
                }
            }
        }
        for (Aro aro : listaDeArosDerecho) {
            aro.update(delta);
            aro.setPosition(aro.getX() - 1.3, aro.getY());
        }
    }

    public void colisionConElCaldero(){
        for (CalderoDeFuego calderito : listaDeCalderos) {
            if (DetectorColisiones.detectarCalderoDeFuego(calderito, charlie)) {
                colisiono = true;
                accion = false;
                restar = false;
                charlie.detenerBonus();
                choqueDelPersonaje(charlie);
            } else if (DetectorColisiones.detectarArribaCalderoDeFuego(calderito, charlie)) {
                pasoXCaldero = true;
                if (!banderaScoreCaldero) {
                    banderaScoreCaldero = true;
                    circusCharlie.setTempScore(200, 180, 250);
                }
            }
        }
    }

    public void sumarPuntosPorObstaculo(){
        if(pasoXAro && !pasoXCaldero){
            if(charlie.getY() == charlie.getPISO()){
                Score.sumarScore(100);
                pasoXAro = false;
            }
        }else if(pasoXCaldero && !pasoXAro){
            if(charlie.getY() == charlie.getPISO()){
                Score.sumarScore(200);
                pasoXCaldero = false;
            }
        }else if(pasoXAro && pasoXCaldero){
            if(charlie.getY() == charlie.getPISO()){
                Score.sumarScore(400);
                pasoXCaldero = false;
                pasoXAro = false;
            }
        }
    }

    public void swapCaldero(double delta){
        for (CalderoDeFuego calderito : listaDeCalderos) {
            calderito.update(delta);
        }
    }

    public void movimientoYSumaBolsita(){
        for(Money bolsita : listaDeBolsaDeMoneda){
            // bolsita.update(delta);
            bolsita.setPosition(bolsita.getX() - 1.3, 260);
            if(DetectorColisiones.detectarBolsita(bolsita, charlie) && !bolsita.getAspiroLaBolsita()){
                if(pasoXCaldero){
                    Score.sumarScore(1000);
                }else {
                    Score.sumarScore(500);
                }
                bolsita.setAspiroLaBolsita(true);
                circusCharlie.setTempScore(500, 180, 250);
            }
        }
    }
}
