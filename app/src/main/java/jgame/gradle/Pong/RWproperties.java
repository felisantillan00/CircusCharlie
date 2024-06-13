package jgame.gradle.Pong;

import java.io.*;
import java.util.*;

public class RWproperties {
    private static Properties prop = new Properties();
    
    public static void writeProperties(String configJuego, String key, String value) {
        String resourseUrl = RWproperties.class.getClassLoader().getResource(configJuego).getPath();
        System.out.println("Entro al writeProperties");
        try (OutputStream input = new FileOutputStream(resourseUrl)) {
            prop.setProperty(key, value);
            prop.store(input, null);
            System.out.println(prop.getProperty(key));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error " + e);
        }
    }

    public static String readProperties(String configJuego,String key) {
        try (InputStream input = RWproperties.class.getClassLoader().getResourceAsStream(configJuego)) {
            if (input == null) {
                System.out.println("No se pudo acceder al archivo .properties");
                return null;
            }
            prop.load(input);
            System.out.println(prop.getProperty(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
