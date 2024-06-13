package jgame.gradle.CircusCharlie;

public class Camara {
	private double x;
	private double y;
	private double resX;
	private double resY;

    public Camara(double x,double y) {
		this.x=x;
		this.y=y;
    }

	public void seguirPersonaje(Charlie charlie, int index){
		Mundo m = Mundo.getInstance();
		this.x =- charlie.getX() + index;
		if (this.x > 0){
			this.x = 0;
		}
		if(this.x <- (m.getWidth() - resX)){
			this.x =- (m.getWidth() - resX);
		}
	}

	public void seguirPersonaje(Charlie charlie, Leon leon, int index){
		Mundo m=Mundo.getInstance();
		this.x =- charlie.getX() + index;
		this.x =- leon.getX() + index;
		if (this.x>0){
			this.x=0;
		}
		if(this.x < -(m.getWidth()-resX)){
			this.x = -(m.getWidth()-resX);
		}
	}
	
	public void setViewPort(double x,double y){
		setRegionVisible(x,y);
	}
	
	public void setRegionVisible(double x,double y){
		resX=x;
		resY=y;
	}
    public void setX(double x){
		this.x=x;
    }

    public void setY(double y){
		this.y=y;
    }

	public double getX(){
		return this.x;
    }

	public double getY(){
		return this.y;
    }
}