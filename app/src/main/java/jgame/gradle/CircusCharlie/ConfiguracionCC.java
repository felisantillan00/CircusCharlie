package jgame.gradle.CircusCharlie;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jgame.gradle.Pong.RWproperties;

public class ConfiguracionCC extends JFrame {
    private JComboBox<String> desactivarSonido;
    private JComboBox<String> cancionesComboBox;
    private JComboBox<String> movimiento;
    private JComboBox<String> salto;
    private JComboBox<String> opcionesVentana;
    String configJuego;

    public ConfiguracionCC() {
        // Configurar la ventana
        setTitle("Configuracion de Pong");
        setSize(400, 400);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        

        JLabel ventanaTexto = new JLabel("Opciones de ventanas");
        String[] opciones = { "Ventana", "Pantalla Completa" };
        opcionesVentana = new JComboBox<>(opciones);

        // Crear y configurar los elementos de la interfaz
        JLabel textoMoviemiento = new JLabel("Teclas de Movimiento");
        String[] teclasMov = { "Flecha izq - Flecha der", "A - D" };
        movimiento = new JComboBox<>(teclasMov);

        JLabel textoSalto = new JLabel("Tecla de Salto");
        String[] teclasSalto = { "X", "Flecha arriba", "W" };
        salto = new JComboBox<>(teclasSalto);

        JLabel cancionesLabel = new JLabel("Seleccionar cancion:");
        String[] canciones = { "El peluca - leon" }; // Lista de canciones disponibles
        cancionesComboBox = new JComboBox<>(canciones);

        JLabel sonido = new JLabel("Sonido:");
        String[] des = { "Activado", "Desactivado" }; 
        desactivarSonido = new JComboBox<>(des);

        JButton guardarButton = new JButton("GUARDAR");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                configJuego = "configuracionCharlie.properties";
                guardarConfiguracion();
            }
        });


        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                configJuego = "configuracionCharlie.properties";
                resetButton();
            }
        });
        // Crear y configurar el panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(16, 2, 10, 0));
        panel.add(ventanaTexto);
        panel.add(opcionesVentana);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(textoMoviemiento);
        panel.add(movimiento);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(textoSalto);
        panel.add(salto);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(sonido);
        panel.add(desactivarSonido);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(cancionesLabel);
        panel.add(cancionesComboBox);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(guardarButton);
        panel.add(reset);

        // Agregar el panel a la ventana
        getContentPane().add(panel);

        // Hacer visible la ventana
        setVisible(true);
    }

    private void guardarConfiguracion() {
        RWproperties.writeProperties(configJuego, "Musica", (String) cancionesComboBox.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Movimiento", (String) movimiento.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Salto", (String) salto.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Ventana", (String) opcionesVentana.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Sonido", (String) desactivarSonido.getSelectedItem());

        if(opcionesVentana.getSelectedItem().equals("Ventana")){
            CircusCharlie.pantallaCompleta("false");
        }else{
            CircusCharlie.pantallaCompleta("true");
        }

        if(desactivarSonido.getSelectedItem().equals("Desactivado")){
            FXPlayer.LEON.stop();
        }
        else{
            FXPlayer.init();
            FXPlayer.LEON.loop();
        }
        // Aquí puedes cerrar la ventana de configuración si es necesario
        dispose();
    }

    private void resetButton(){
        RWproperties.writeProperties(configJuego, "Musica", "dbz");
        RWproperties.writeProperties(configJuego, "Movimiento", "Flecha izq - Flecha der");
        RWproperties.writeProperties(configJuego, "Salto", "ESPACE");
        RWproperties.writeProperties(configJuego, "Ventana", "Ventana");
        RWproperties.writeProperties(configJuego, "Sonido", "Activado");
        // Aquí puedes cerrar la ventana de configuración si es necesario
        dispose();
    }

    public static void main(String[] args) {
        new ConfiguracionCC();
    }
}
