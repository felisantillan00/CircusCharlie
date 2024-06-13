package jgame.gradle.CircusCharlie;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreBD {
    private static final String NOMBRE_BASEDATOS = "db/score.db";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public void inicializar() {
        stmt = null;
        pstmt = null;
        try {
            String url = "jdbc:sqlite:" + ScoreBD.class.getClassLoader().getResource(NOMBRE_BASEDATOS);
            System.out.println(url);
            conn = DriverManager.getConnection(url);
            System.out.println("Conectado a SQLite");
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public static ResultSet getData() throws SQLException {
        stmt = conn.createStatement();
        String sql = "SELECT * FROM gamescore ORDER BY puntaje DESC LIMIT 10";
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public static void insert(String nombre, int score) {
        String sql = "INSERT INTO gamescore(nombre, puntaje, fecha) VALUES(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, score);
            pstmt.setString(3, DATE_FORMAT.format(new Date()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public ScoreBD() {
        inicializar();
        insert("sofi", 10000);
        insert("facu", 8800);
    }

}
