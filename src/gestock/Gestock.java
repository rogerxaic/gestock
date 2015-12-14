/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.baseProducts.BaseProduct;
import gestock.util.Curl;
import gestock.util.Tools;
import gestock.window.Interface;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author rogerxaic
 */
public class Gestock {

    public User mainUser;
    protected Properties prop;
    protected String fs = System.getProperty("file.separator");
    protected String filepath = System.getProperty("user.home") + fs
            + (Tools.isWindows() ? "AppData" + fs + "Roaming" + fs + "gestock" : ".config" + fs + "gestock");
    protected String filename = "config.properties";
    protected LinkedList<BaseProduct> catalogue;

    public Gestock() {


        if (!init()) {
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
            System.out.println("Loading properties file");
            prop = getProperties(filepath + fs + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainUser = new User(prop);
        catalogue = new LinkedList<>();

        try {
            Curl curl = new Curl("http://gestock.xaic.cat/api/baseproducts");
            curl.run();
            JSONArray baseProductsArray = new JSONArray(curl.getResponse());
            System.out.println(baseProductsArray.get(0));
            for (int i = 0; i < baseProductsArray.length(); i++) {
                BaseProduct product = new BaseProduct(baseProductsArray.get(i));
                catalogue.add(product);
            }
            System.out.println(catalogue);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        EventQueue.invokeLater(() -> {
            new Interface(app);
            //new ProductWindow(app);
        });
    }

    /**
     * Creates the filepath directory if it does not exist.
     * Checks if there's the properties file inside the directory.
     *
     * @return true if the file exists, else false.
     */
    private boolean init() {
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

    public void saveProperties() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filepath + fs + filename);
            prop.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User getUser() {
        return mainUser;
    }

    public LinkedList getCatalogue() {
        return this.catalogue;
    }
}
