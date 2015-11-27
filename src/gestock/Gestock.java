/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.util.Tools;
import gestock.window.Interface;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author rogerxaic
 */
public class Gestock {

    public User mainUser;
    protected Properties prop;

    public Gestock() {
        String fs = System.getProperty("file.separator");
        String filepath = System.getProperty("user.home") + fs;
        filepath += Tools.isWindows() ? "AppData" + fs + "Roaming" + fs + "gestock" : ".config" + fs + "gestock";
        String filename = "config.properties";

        if (!init(filepath, fs, filename)) {
            //gotta create it
            List<String> props = Arrays.asList("userName:" + System.getProperty("user.name"));
            try {
                Files.write(Paths.get(filepath + fs + filename), props);
                System.out.println("Created a basic properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Trying to load props file");
            prop = getProperties(filepath + fs + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainUser = new User(prop);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Gestock app = new Gestock();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {
            evt.printStackTrace();
        }
        EventQueue.invokeLater(() -> new Interface(app));
    }

    /**
     * Creates the filepath directory if it does not exist.
     * Checks if there's the properties file inside the directory.
     *
     * @param filepath Where the properties file should be located
     * @param fs       System file separator
     * @param filename Properties file
     * @return true if the file exists, else false.
     */
    private boolean init(String filepath, String fs, String filename) {
        File f;
        boolean bool = false;
        try {
            f = new File(filepath);
            if (!f.exists() || (f.exists() && !f.isDirectory())) {
                // create directory of app config parameters if it does not exist
                bool = f.mkdirs();
            }
            // print status
            System.out.println("Directory(" + filepath + ") created? " + bool);

        } catch (Exception e) {
            e.printStackTrace();
        }

        File configFile = new File(filepath + fs + filename);

        return configFile.exists() && !configFile.isDirectory();
    }

    private Properties getProperties(String filename) throws IOException {
        FileInputStream inputStream = null;
        Properties prop = new Properties();
        try {
//            String propFileName = "config.properties";

            inputStream = new FileInputStream(filename);

            //                prop.load(inputStream);
            prop.load(inputStream);
            // get the property value and print it out
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return prop;
    }

    public User getUser() {
        return mainUser;
    }
}
