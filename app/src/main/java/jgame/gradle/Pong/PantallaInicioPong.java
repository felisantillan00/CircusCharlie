package jgame.gradle.Pong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PantallaInicioPong extends JFrame {
    private BufferedImage imagenFondo;

    public PantallaInicioPong() {
        setTitle("Inicio de Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600); // La pantalla tiene el mismo tamaño que el juego
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(false); // Evitar redimensionamiento
        // Cargar la imagen de fondo
        try {
            imagenFondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/JuegoPong/Pong.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error" + e);
        }
        // Crear un JPanel personalizado con la imagen de fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        // Configurar el panelFondo
        panelFondo.setLayout(new GridLayout());
        JLabel iniciarJuegoLabel = new JLabel("<html><h1>Iniciar Juego</h1></html>", SwingConstants.CENTER);
        iniciarJuegoLabel.setForeground(Color.WHITE); // Texto blanco
        iniciarJuegoLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 40)); // Tamaño y estilo del texto
        iniciarJuegoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                iniciarJuego(); // Al hacer clic en el texto, iniciar el juego
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iniciarJuegoLabel.setForeground(Color.YELLOW); // Cambiar color al pasar el ratón
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iniciarJuegoLabel.setForeground(Color.WHITE); // Restaurar color al salir del texto
            }
        });
        JLabel configuracion = new JLabel("<html><h1>Configuracion</h1></html>", SwingConstants.CENTER);
        configuracion.setForeground(Color.WHITE); // Texto blanco
        configuracion.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 40)); // Tamaño y estilo del texto
        configuracion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // iniciar el juego
                new ConfiguracionPong();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                configuracion.setForeground(Color.YELLOW); // Cambiar color al pasar el ratón
            }
            @Override
            public void mouseExited(MouseEvent e) {
                configuracion.setForeground(Color.WHITE); // Restaurar color al salir del texto
            }
        });

        panelFondo.add(iniciarJuegoLabel);
        panelFondo.add(configuracion);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelFondo, BorderLayout.CENTER);
        Sonido.iniciar(RWproperties.readProperties(Pong.configJuego, "Musica"));
        Sonido.loop();
        setVisible(true);
    }

    private void iniciarJuego() {
        // Al hacer clic en "Iniciar Juego", crear una instancia de GameFrame para
        // arrancar el juego
        Sonido.parar();
        Pong juego = new Pong();
        Thread t = new Thread() {
            public void run() {
                juego.run(1.0 / 60.0);
            }
        };
        t.start();
        // Cerrar la pantalla de inicio
        dispose();
    }

    public static void main(String[] args) {
        new PantallaInicioPong();
    }
}
