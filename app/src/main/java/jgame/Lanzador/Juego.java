package jgame.Lanzador;

import javax.swing.ImageIcon;

public abstract class Juego {
    private String nombre;
    private String desarrolladores;
    private String version;
    private String descripcion;
    private ImageIcon imagenPortada;
    private boolean implementado;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesarrolladores() {
        return this.desarrolladores;
    }

    public void setDesarrolladores(String desarrolladores) {
        this.desarrolladores = desarrolladores;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ImageIcon getImagenPortada() {
        return this.imagenPortada;
    }

    public void setImagenPortada(ImageIcon imagenPortada) {
        this.imagenPortada = imagenPortada;
    }


    public boolean isImplementado() {
        return this.implementado;
    }

    public void setImplementado(boolean implementado) {
        this.implementado = implementado;
    }

    public abstract void run();

}
