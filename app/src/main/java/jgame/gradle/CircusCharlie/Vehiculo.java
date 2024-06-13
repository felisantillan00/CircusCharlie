package jgame.gradle.CircusCharlie;
import javax.imageio.*;
import java.util.*;

public abstract class Vehiculo extends ObjetoGrafico {
    protected int direccionAngulo = 0;
	protected double PISO;
	protected double velocityX = 4.0;
	protected double velocityY = 0.0;
	protected double angulo = 0.0;
	protected int estadoActual;
    protected final int ESTADO_SALTANDO = 2;
	protected final int ESTADO_QUIETO = -1;
	protected boolean saltando = false;
	protected boolean enElSuelo = true;

    public Vehiculo(String filename) {
		super(filename);
    }

    public void setImagen(String img){
		try {
			this.imagen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(img)));
		} catch (Exception e) {
			System.out.println("ERROR...");
			e.printStackTrace();
		}
	}

    public void setPISO(double d){
		this.PISO = d;
	}

	public double getPISO(){
		return this.PISO;
	}

	public void left() {
		this.positionX -= velocityX;
		direccionAngulo =- 1;
	}

	public void right() {
		positionX += velocityX;
		direccionAngulo = 1;
	}

	public void quieto(){
		if(saltando){
			estadoActual = ESTADO_QUIETO;
		}
	}
	
	public void jump(){
		if (enElSuelo) {
			velocityY =- 12.0;
			enElSuelo = false;
			saltando = true;
			estadoActual = ESTADO_SALTANDO;
		}
	}

	public void update(double delta){}
}