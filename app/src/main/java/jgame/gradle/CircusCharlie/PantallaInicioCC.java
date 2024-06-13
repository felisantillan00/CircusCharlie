package jgame.gradle.CircusCharlie;
import javax.swing.*;
import jgame.gradle.FontManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class PantallaInicioCC extends JFrame {
    private BufferedImage tituloImagen;
    public static String nombreJugador;

    public PantallaInicioCC() {
        setTitle("Circus Charlie");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Cargar la imagen del título
        try {
            tituloImagen = ImageIO.read(getClass().getClassLoader().getResource("imagenes/JuegoCircusCharlie/Generales/Inicio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Crear el panel principal
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                FontManager.getInstance();
                super.paintComponent(g);
                setBackground(Color.BLACK);
                // Dibujar la imagen del título en el centro
                int imgX = (getWidth() - tituloImagen.getWidth()) / 2;
                int imgY = (getHeight() - tituloImagen.getHeight()) / 4;
                g.drawImage(tituloImagen, imgX, imgY, this);
                // Dibujar los textos de opciones
                g.setColor(Color.WHITE);
                g.setFont(new Font("Pixel Emulator", Font.PLAIN, 20));
                String iniciarTexto = "Iniciar Juego";
                int iniciarWidth = g.getFontMetrics().stringWidth(iniciarTexto);
                int iniciarX = (getWidth() - iniciarWidth) / 2;
                int iniciarY = imgY + tituloImagen.getHeight() + 50;
                g.drawString(iniciarTexto, iniciarX, iniciarY);
                String configTexto = "Configuracion";
                int configWidth = g.getFontMetrics().stringWidth(configTexto);
                int configX = (getWidth() - configWidth) / 2;
                int configY = iniciarY + 50;
                g.drawString(configTexto, configX, configY);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                int iniciarY = (getHeight() - tituloImagen.getHeight()) / 4 + tituloImagen.getHeight() + 50;
                int configY = iniciarY + 50;
                // Verificar si se hizo clic en "Iniciar Juego"
                if (y >= iniciarY - 30 && y <= iniciarY) {
                    iniciarJuego();
                }
                // Verificar si se hizo clic en "Configuracion"
                if (y >= configY - 30 && y <= configY) {
                    abrirConfiguracion();
                }
            }
        });
        add(panel);
        
        FXPlayer.init();
        FXPlayer.LEON.loop();

        setVisible(true);
    }

    private void iniciarJuego() {
        FXPlayer.LEON.stop();
        CircusCharlie game = new CircusCharlie();
        Thread t = new Thread(){
            public void run(){
                nombreJugador = JOptionPane.showInputDialog(null, "Inserte el nombre del jugador: ","Nombre del jugador", JOptionPane.PLAIN_MESSAGE);
                if (nombreJugador != null && !nombreJugador.trim().isEmpty()) {
                    game.run(1.0 / 60.0);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para continuar", "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        t.start();
        dispose();
    }

    private void abrirConfiguracion() {
        // Aquí debes implementar el código para abrir la configuración
        System.out.println("Abriendo configuración...");
        new ConfiguracionCC();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicioCC());
    }
}
