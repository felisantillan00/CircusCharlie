package jgame.gradle.Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiguracionPong extends JFrame {
    private JComboBox<String> cancionesComboBox;
    private JComboBox<String> desactivarSonido;
    private JComboBox<String> J1;
    private JComboBox<String> J2;
    private JComboBox<String> opcionesVentana;
    private JComboBox<String> coloresJ1;
    private JComboBox<String> coloresJ2;
    private JComboBox<String> modoDeJuego;
    String configJuego;

    // private RWproperties prop = new RWproperties();

    public ConfiguracionPong() {
        // Configurar la ventana
        setTitle("Configuracion de Pong");
        setSize(400, 500);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        JLabel ventanaTexto = new JLabel("Opciones de ventanas");
        String[] opciones = { "Ventana", "Pantalla Completa" };
        opcionesVentana = new JComboBox<>(opciones);
        // Crear y configurar los elementos de la interfaz
        JLabel jugador1Label = new JLabel("Teclas Jugador 1:");
        String[] teclasJ1 = { "w - s", "e - d" };
        J1 = new JComboBox<>(teclasJ1);
        JLabel jugador2Label = new JLabel("Teclas Jugador 2:");
        String[] teclasJ2 = { "UP - DOWN", "o - l" };
        J2 = new JComboBox<>(teclasJ2);
        JLabel colorpaddleJ1 = new JLabel("Color de la paleta Jugador 1");
        String[] colorJ1 = { "azul", "amarillo" };
        coloresJ1 = new JComboBox<>(colorJ1);
        JLabel colorpaddleJ2 = new JLabel("Color de la paleta Jugador 2");
        String[] colorJ2 = { "rojo", "azul" };
        coloresJ2 = new JComboBox<>(colorJ2);
        JLabel gameMode = new JLabel("Seleccione el modo de juego");
        String[] modos = { "facilito", "Modo harcord extremo" };
        modoDeJuego = new JComboBox<>(modos);
        JLabel cancionesLabel = new JLabel("Seleccionar cancion:");
        String[] canciones = { "dbz", "muchachos" }; // Lista de canciones disponibles
        cancionesComboBox = new JComboBox<>(canciones);
        JLabel sonido = new JLabel("Sonido:");
        String[] des = { "Activado", "Desactivado" };
        desactivarSonido = new JComboBox<>(des);
        JButton guardarButton = new JButton("GUARDAR");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                configJuego = "configuracionPong.properties";
                guardarConfiguracion();
            }
        });
        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configJuego = "configuracionPong.properties";
                resetButton();
            }
        });

        // Crear y configurar el panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(18, 2, 10, 0));
        panel.add(ventanaTexto);
        panel.add(opcionesVentana);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(jugador1Label);
        panel.add(J1);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(jugador2Label);
        panel.add(J2);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(colorpaddleJ1);
        panel.add(coloresJ1);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(colorpaddleJ2);
        panel.add(coloresJ2);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(gameMode);
        panel.add(modoDeJuego);
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
        RWproperties.writeProperties(configJuego, "ColorJ1", (String) coloresJ1.getSelectedItem());
        RWproperties.writeProperties(configJuego, "ColorJ2", (String) coloresJ2.getSelectedItem());
        RWproperties.writeProperties(configJuego, "TeclasJ1", (String) J1.getSelectedItem());
        RWproperties.writeProperties(configJuego, "TeclasJ2", (String) J2.getSelectedItem());
        RWproperties.writeProperties(configJuego, "ModoJuego", (String) modoDeJuego.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Musica", (String) cancionesComboBox.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Ventana", (String) opcionesVentana.getSelectedItem());
        RWproperties.writeProperties(configJuego, "Sonido", (String) desactivarSonido.getSelectedItem());
        if (opcionesVentana.getSelectedItem().equals("Ventana")) {
            Pong.pantallaCompleta("false");
        } else {
            Pong.pantallaCompleta("true");
        }

        Sonido.cambiarCancion((String) cancionesComboBox.getSelectedItem());
        if(desactivarSonido.getSelectedItem().equals("Desactivado")){
            Sonido.parar();
        }
        // Aquí puedes cerrar la ventana de configuración si es necesario
        dispose();
    }

    private void resetButton() {
        RWproperties.writeProperties(configJuego, "ColorJ1", "azul");
        RWproperties.writeProperties(configJuego, "ColorJ2", "rojo");
        RWproperties.writeProperties(configJuego, "TeclasJ1", "w - s");
        RWproperties.writeProperties(configJuego, "TeclasJ2", "UP - DOWN");
        RWproperties.writeProperties(configJuego, "ModoJuego", "facilito");
        RWproperties.writeProperties(configJuego, "Musica", "dbz");
        RWproperties.writeProperties(configJuego, "Ventana", "Ventana");
        RWproperties.writeProperties(configJuego, "Sonido", "Activado");
        Sonido.cambiarCancion((String) cancionesComboBox.getSelectedItem());
        // Aquí puedes cerrar la ventana de configuración si es necesario
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfiguracionPong());
    }
}
