
package jgame.Lanzador.gui;
import jgame.Lanzador.Juego;
import jgame.Lanzador.SistemaDeJuego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class RecuadroJuego extends JPanel implements ActionListener{

    private JButton btn;
    private Juego juego;
    private SistemaDeJuego sistemaDeJuego;

    public RecuadroJuego(Juego juego, SistemaDeJuego sistemaDeJuego){
        this.juego = juego;
        this.sistemaDeJuego = sistemaDeJuego;
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5, true));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400,450));

        JPanel tituloPanel = new JPanel();
        JLabel titulo = new JLabel(juego.getNombre());
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        tituloPanel.add(titulo);

        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.Y_AXIS));
        abajo.add(new JLabel("Descripcion " + juego.getDescripcion()));
        abajo.add(new JLabel("Desarrolladores " + juego.getDesarrolladores()));
        abajo.add(new JLabel("Version " + juego.getVersion()));

        JPanel foto = new JPanel();
        btn = new JButton(juego.getImagenPortada());
        btn.addActionListener(this);
        foto.add(btn);
        
        this.add(tituloPanel, BorderLayout.PAGE_START);
        this.add(foto, BorderLayout.CENTER);
        this.add(abajo, BorderLayout.PAGE_END);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(juego.isImplementado()){
            sistemaDeJuego.setVisible(false);
        }else{
            sistemaDeJuego.setVisible(true);
        }
        if(!e.getSource().equals(btn)) return;
        juego.run();
        
    }
    
}
