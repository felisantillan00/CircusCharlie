package jgame.gradle.CircusCharlie;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Leon extends Vehiculo{
	private double gravity = 0.43;
	private	double andandoLeon;
	private boolean l1;
	private boolean l2;
	
    public Leon(String filename) {
        super(filename);
        try{
            if(images.isEmpty()){
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/imagenes/JuegoCircusCharlie/ImagenNivel1/leon.png")));
				images.add(imagen);
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/imagenes/JuegoCircusCharlie/ImagenNivel1/leonCorriendo1.png")));
				images.add(imagen); //Imagen leon corriendo
				imagen = ImageIO.read(Objects.requireNonNull(getClass().getResource("/imagenes/JuegoCircusCharlie/ImagenNivel1/leonCorriendo2.png")));
				images.add(imagen);
            }
        }catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }

	public void setImagen(BufferedImage img){
		this.imagen = img;
	}

	public void jump(){
		super.jump();
		this.setImagen(images.get(1));
	}
	
	public void quieto(){
		super.quieto();
		this.setImagen(images.get(0));
	}

	public void left() {
		super.left();
		this.cambioImagenLeon();
	}

	public void right() {
		super.right();
		this.cambioImagenLeon();
	}

    public void cambioImagenLeon(){
		andandoLeon++;
		if(andandoLeon >=10 && !saltando){
			if(l1 && l2){
				this.setImagen(images.get(1));
				l1 = false;
				l2 = false;
			}else if(!l2 &&! l1){
				this.setImagen(images.get(2));
				l1=true;
			}else if(l1 &&! l2){
				this.setImagen(images.get(0));
				l2=true;
			}
			andandoLeon = 0;
		}
	}

	public void update(double delta){
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
			angulo = 0;
			/*ya toco el piso*/
		}
		if ( velocityY != 0.0){
			saltando = true;
			//mientras este saltando
		}
		if(velocityY == 0.0){
			saltando = false;
		}	
    }
}