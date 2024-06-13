package jgame.gradle.CircusCharlie;
import java.awt.*;
import java.awt.geom.*;

public class Mundo {
  private static final int ANCHO_MUNDO=640*13;
  private static final int ALTO_MUNDO=600;
  private static Mundo INSTANCE = null;
  private Rectangle2D mundo;

  private Mundo(){
    mundo=new Rectangle2D.Double(0,0,ANCHO_MUNDO,ALTO_MUNDO);
  }

  private static void createInstance() {
    if (INSTANCE == null) {
      // S�lo se accede a la zona sincronizada
      // cuando la instancia no est� creada
      synchronized(Mundo.class) {
        // En la zona sincronizada ser�a necesario volver
        // a comprobar que no se ha creado la instancia
        if (INSTANCE == null) {
          INSTANCE = new Mundo();
        }
      }
    }
  }

  public static Mundo getInstance() {
    if (INSTANCE == null) createInstance();
    return INSTANCE;
  }

  public boolean contains(int x, int y){
    return mundo.contains(x,y);
  }

  public boolean contains(int x, int y, int w,  int h){
    return mundo.contains(x,y,w,h);
  }

  public void setLimitesMundo(int w,int h){
    mundo=new Rectangle2D.Double(0,0,w,h);
	}
  
  public Rectangle2D getRectangle(){
    return this.mundo;
  }

  public float getWidth(){
    return (float)mundo.getWidth();
  }

  public float getHeight(){
    return (float)mundo.getHeight();
  }

  public void display(Graphics2D g2) {}
}