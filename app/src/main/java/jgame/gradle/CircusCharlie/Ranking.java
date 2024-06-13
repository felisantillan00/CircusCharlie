package jgame.gradle.CircusCharlie;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import jgame.gradle.FontManager;

public class Ranking extends JFrame{
    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<Integer> puntuaciones = new ArrayList<>();
    private ArrayList<String> fecha = new ArrayList<>();
    
    public Ranking() throws SQLException{
        obtenerDatos();
        FontManager.getInstance();
        for (String string : nombres) {
            System.out.println(string);
        }
        this.setTitle("Ranking");
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(0,1));
        this.add(panel);
        JLabel title = new JLabel("Ranking", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Pixel Emulator",Font.BOLD, 24));
        panel.add(title);
        for(int i = 0; i<nombres.size(); i++){
            JLabel scoreLabel = new JLabel((i + 1) + ". " 
            + nombres.get(i) + " - " 
            + puntuaciones.get(i) + " - " 
            + fecha.get(i)
            , SwingConstants.CENTER);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(new Font("Pixel Emulator", Font.PLAIN, 18));
            panel.add(scoreLabel);
        }
        JLabel jugarDeNuevo = new JLabel("Jugar de nuevo", SwingConstants.LEFT);
        jugarDeNuevo.setForeground(Color.WHITE);
        jugarDeNuevo.setFont(new Font("Pixel Emulator", Font.PLAIN,18));
        jugarDeNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jugarDeNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PantallaInicioCC();
            }
        });
        this.setVisible(true);
    }

    private void obtenerDatos() throws SQLException {
        ResultSet rs = ScoreBD.getData();
        try {
            while (rs != null && rs.next()) {
                nombres.add(rs.getString("nombre"));
                puntuaciones.add(rs.getInt("puntaje"));
                fecha.add(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws SQLException {
        new Ranking();
    }
}
