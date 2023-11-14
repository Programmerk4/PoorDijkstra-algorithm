import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
public class Main extends JFrame {
    //declare variables
    public static String version;
    public static int resX;
    public static int resY;
    public static Window window = new Window();
    public static int scale;
    public static String background, vertexColor, textColor;
    public Main() {
        //Fetch initial variables from ini file
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("properties.ini"));
            version = properties.getProperty("version");
            resX = Integer.parseInt(properties.getProperty("resx"));
            resY = Integer.parseInt(properties.getProperty("resy"));
            scale = Integer.parseInt(properties.getProperty("scale"));
            background = properties.getProperty("background");
            vertexColor = properties.getProperty("vertexColor");
            textColor = properties.getProperty("textColor");
        } catch (Exception e) {
            System.out.println("Failed to load variables!!! ");
            System.out.println(e);
        }
        //create the main window
        setTitle("Graphical calculator");
        Dimension dimension = new Dimension(resX, resY);
        setPreferredSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(window);
        window.requestFocus();
        pack();
    }
    //This is executed when the user logs in
    public static void startProgram() {
        Main main = new Main();
        //create an exemplar default graph
        window.createVertex(100, 300, "a");
        window.createVertex(400, 500, "b");
        window.createVertex(650, 100, "c");
        window.createVertex(550, 300, "d");
        window.createVertex(1000, 300, "e");
        window.createVertex(1000, 600, "f");
        window.createVertex(2600, 2600, " ");
        window.joinVertices("a", "b", 70);
        window.joinVertices("a", "d", 120);
        window.joinVertices("d", "c", 40);
        window.joinVertices("c", "e", 90);
        window.joinVertices("d", "e", 60);
        window.joinVertices("e", "f", 70);
        window.joinVertices("d", "f", 160);
        window.joinVertices("d", "b", 40);
        window.joinVertices("b", "f", 200);
    }
    public static void main(String[] args) throws Exception {
        //start the program with the login screen
        Login login = new Login();
    }
}

