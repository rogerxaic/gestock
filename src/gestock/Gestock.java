/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.entity.MilkBaseProduct;
import gestock.entity.User;
import gestock.resources.views.GestockView;
import gestock.resources.views.components.Launcher;
import gestock.util.Constants;
import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * @author rogerxaic
 */
public class Gestock extends Observable {

    public User mainUser;
    private Properties prop;
    private String fs = System.getProperty("file.separator");
    private String filepath = System.getProperty("user.home") + fs
            + (Tools.isWindows() ? "AppData" + fs + "Roaming" + fs + "gestock" : ".config" + fs + "gestock");
    private String filename = "config.properties";
    private LinkedList<BaseProduct> catalogue;
    private List<BoughtProduct> pantry;

    public Gestock() {
        Launcher launcher = new Launcher();
        /**
         * Loading the properties if they exist, create a basic properties file otherwise.
         */
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
            System.out.println("Loading properties file...");
            launcher.setText("Loading properties file...");
            prop = getProperties(filepath + fs + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainUser = new User(prop);
        catalogue = new LinkedList<>();
        pantry = new LinkedList<>();

        /**
         * We fetch the products from the database using the gestock api. Created /w Symfony 2.
         * The integration of MySQL-Java wasn't possible because the CIPC's db didn't work.
         */
        try {
            System.out.print("Fetching database content... ");
            launcher.setText("Fetching database products from Internet...");
            Curl curl = new Curl("http://gestock.xaic.cat/api/baseproducts");
            curl.run();
            JSONArray baseProductsArray = new JSONArray(curl.getResponse());
            launcher.setText("Adding products to catalogue...");
            for (int i = 0; i < baseProductsArray.length(); i++) {
                try {
                    launcher.setText("Adding product " + (i + 1) + " of " + baseProductsArray.length() + " to catalogue...");
                    BaseProduct product = new BaseProduct(baseProductsArray.get(i));
                    catalogue.add(product);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("\nThere has been a problem... Do you have an active internet connection?");
            e.printStackTrace();
        }
        BaseProduct b = new MilkBaseProduct(0, "Lait 1.5L Candia", "", "", "Candia", new HashMap<>(), 1, 1);
        catalogue.add(b);
        launcher.dispose();
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
        EventQueue.invokeLater(() -> new GestockView(app));
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

    public void addToCatalogue(BaseProduct baseProduct) {
        this.catalogue.add(baseProduct);
        setChanged();
        notifyObservers(Constants.OBSERVER_PRODUCT_CREATED);
    }

    public List<BoughtProduct> getPantry() {
        return pantry;
    }

    public void setPantry(List<BoughtProduct> pantry) {
        this.pantry = pantry;
    }

    public void addToPantry(BoughtProduct bp) {
        this.pantry.add(bp);
        setChanged();
        notifyObservers(Constants.OBSERVER_PANTRY_PRODUCT_CREATED);
    }
}