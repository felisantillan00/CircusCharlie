package jgame.gradle.CircusCharlie;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

//import processing.core.*;
///   http://jsfiddle.net/LyM87/
/// https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java/37758533
public class Charlie extends ObjetoGrafico implements ObjetoMovible {
	private final int ESTADO_QUIETO = -1;
	private int estadoActual;
	private int direccionAngulo= 1;
	private int andando = 0;
	private double velocidadCaida = 0;
	private double gravedad = 9.8;
	private double velocityX = 4.0;
	private double velocityY = 0.0;
	private double gravity = 0.43;
	private double angulo = 0.0;
	private double PISO;
	private boolean band = true;
	private boolean band0 = true;
	private boolean enElSuelo = false;
	private boolean saltando=false;
	private boolean enLaPelota = true;

	private Score puntosJugador = new Score();

	public Charlie(String filename){
		super(filename);
		try{
			if(images.isEmpty()){
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/Generales/charlie.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/Generales/charlie2.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga1.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga2.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/ImagenNivel2/charlieSoga3.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/Generales/charlieVictoria1.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoCircusCharlie/Generales/charlieVictoria2.png")));
				images.add(imagen);
			}
		} catch (IOException e){
			throw new RuntimeException("Error al cargar la imagen del caldero", e);
		}
	}

	public void setImagen(String img){
		try {
			this.imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(img)));
		} catch (Exception e) {
			System.out.println("ERROR...");
			e.printStackTrace();
		}
	}

	public void setImagen(BufferedImage img){
		this.imagen = img;
	}
	
	public void setPISO(double d){
		this.PISO = d;
	}

	public double getPISO(){
		return PISO;
	}

	public void setVelocidadCaida(double nuevaVelocidad){
		this.velocidadCaida = nuevaVelocidad;
	}

	public double getVelocidadCaida(){
		return this.velocidadCaida;
	}

	public void setGravedad(double nuevaGravedad){
		this.gravedad = nuevaGravedad;
	}

	public double getGravedad(){
		return this.gravedad;
	}

	public void setEnLaPelota(boolean montado){
		this.enLaPelota = montado;
	}

	public boolean getEnLaPelota(){
		return this.enLaPelota;
	}

	public void jump() {
		if (enElSuelo) {
			velocityY = -12.0;
			enElSuelo = false;
			saltando = true;
		}
	}
	
	public void quieto() {
		estadoActual = ESTADO_QUIETO;
	}
	
	public boolean saltando(){
		return saltando;
	}
	public void left() {
		positionX -= velocityX;
		direccionAngulo=-1;
	}

	public void right() {
		positionX += velocityX;
		direccionAngulo= 1;
	} 

	public void cambioImagen(){ // Metodo para el cambio de imagen del nivel 1
		andando++;
		if (andando >= 15) {
			if (band) {
				this.setImagen(images.get(1)); // Establece la imagen de la posición 1
				band = false;
			} else {
				this.setImagen(images.get(0)); // Establece la imagen de la posición 0
				band = true;
			}
			andando = 0; // Reinicia el contador
		}
	}

	public void cambioImagen1(){ // Metodo para el cambio de imagen del nivel 2 y 3
		andando++;
		if (andando >= 15) {
			if (band && band0) {
				this.setImagen(images.get(3)); // Establece la imagen de la posición 3
				band = false; 
				band0 = false;
			} else if (!band && !band0) {
				this.setImagen(images.get(4)); // Establece la imagen de la posición 4
				band = true;
			} else if (band && !band0) {
				this.setImagen(images.get(2)); // Establece la imagen de la posición 2
				band0 = true;
			}
			andando = 0; // Reinicia el contador
		}
	}
	//Animacion de cuando charlie llega a la meta
	public void updateLlegadaMeta(double delta){
		andando++;
		if(andando >= 15){
			if(band){
				this.setImagen(images.get(5)); // Establece la imagen de la posición 5
				band = false;
			}else{
				this.setImagen(images.get(6)); // Establece la imagen de la posición 6
				band = true;
			}
			andando = 0;
		}
    }

	public void update(double delta) {
		velocityY += gravity;
		positionY += velocityY;
		Mundo m = Mundo.getInstance();
		/* Rebota contra los margenes X del mundo */
		if ((positionX+ (this.getWidth())) > m.getWidth()) {
			positionX = m.getWidth() - (this.getWidth());
		}
		/* Rebota contra la X=0 del mundo */
		if ((positionX) < 0) {
			positionX = 0;
		}
		if(positionY > PISO){
			positionY = PISO;
			velocityY = 0.0;
			enElSuelo = true;
			angulo=0;
			/*ya toco el piso*/
		}
		if ( velocityY != 0.0){
			saltando = true;
			//mientras este saltando
		}
		if(velocityY == 0.0){
			saltando = false;
		}
		puntosJugador.update();
	}

	public void display(Graphics2D g2) {
		/*Redefinicion de Display para poder hacer la rotacion cuando salta*/
        AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(angulo), this.getX() + getWidth()/2, this.getY() + getHeight()/2);
		AffineTransform old = g2.getTransform();
		g2.transform(transform);
		g2.drawImage(imagen,(int) this.positionX,(int) this.positionY,null);
		g2.setTransform(old);
	}

	public void displayScore(Graphics2D g){
		puntosJugador.display(g);
	}

	public void gameOver(){
		puntosJugador.gameOver();
	}

	public void restarVida(int vida){
		puntosJugador.restarBida(vida);
	}
	
	public int getVida(){
		System.out.println(puntosJugador.getVida());
		return puntosJugador.getVida();
	}

	public void imagenNivel(){
		puntosJugador.imagenNivel();
    }

	public void nivel(int nivel){
		puntosJugador.nivelActual(nivel);
	}

	public void sumarBonusScore(){
		puntosJugador.detenerDescuentoBonus();
	}

	public void detenerBonus(){
		puntosJugador.detenerDescuento();
	} 

	public void reiniciarDescuento(){
		puntosJugador.reiniciarDescuento();
	}

	public void setVida(int vida){
		puntosJugador.setVida(vida);
	}
	
	public int getScore(){
		return Score.getScore();
	}

	public void scorePred(){
		puntosJugador.scorePred();
	}

	public void bonusPred(){
		puntosJugador.bonusPred();
	}

	public void continuarDescuento(){
		puntosJugador.continuarDescuento();
	}
}