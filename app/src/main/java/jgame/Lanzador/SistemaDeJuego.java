package jgame.Lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jgame.Lanzador.gui.RecuadroJuego;
import jgame.gradle.CircusCharlie.JuegoCircusCharlie;
import jgame.gradle.MarioBross.MarioBross;
import jgame.gradle.Pong.JuegoPong;

public class SistemaDeJuego extends JFrame{
    public SistemaDeJuego(){
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.DARK_GRAY);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.DARK_GRAY);
        JLabel titulo = new JLabel("SISTEMA DE JUEGO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        panelTitulo.add(titulo);

        JPanel juegos = new JPanel();
        juegos.setLayout(new FlowLayout());

        //add juegos 
        juegos.add(new RecuadroJuego(new JuegoPong(), this));
        juegos.add(new RecuadroJuego(new MarioBross(), this));
        juegos.add(new RecuadroJuego(new JuegoCircusCharlie(), this));

        this.add(juegos, BorderLayout.CENTER);
        this.add(panelTitulo, BorderLayout.NORTH);

        this.pack();
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
    
}
