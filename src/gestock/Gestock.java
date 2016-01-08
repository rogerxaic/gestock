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
    public ResourceBundle messages;
    private Properties prop;
    private String fs = System.getProperty("file.separator");
    private String filepath = System.getProperty("user.home") + fs
            + (Tools.isWindows() ? "AppData" + fs + "Roaming" + fs + "gestock" : ".config" + fs + "gestock");
    private String configProperties = "config.properties";
    private LinkedList<BaseProduct> catalogue;
    private List<BoughtProduct> pantry;

    public Gestock() {
        Launcher launcher = new Launcher();

        /**
         * We fetch the user system's language and we instantiate the messages ResourceBundle
         * in order to have the init messages
         */
        String language = (System.getProperty("user.language"));
        String country = (System.getProperty("user.country"));
        Locale.setDefault(new Locale(language, country));
        messages = ResourceBundle.getBundle("gestock.resources.lang.MessagesBundle", Locale.getDefault());

        /**
         * Loading the properties if they exist, create a basic properties file otherwise.
         */
        if (!init()) {
            //gotta create it
            List<String> props = Arrays.asList("user.name:" + System.getProperty("user.name"),
                    "user.language" + language,
                    "user.country" + country);
            try {
                Files.write(Paths.get(filepath + fs + configProperties), props);
                System.out.println("Created a basic properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("Loading properties file...");
            launcher.setText(messages.getString("loadingProperties"));
            prop = getProperties(filepath + fs + configProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * We fetch the actual user's language selection
         */
        language = prop.getProperty("user.language", System.getProperty("user.language"));
        country = prop.getProperty("user.country", System.getProperty("user.country"));
        Locale.setDefault(new Locale(language, country));
        messages = ResourceBundle.getBundle("gestock.resources.lang.MessagesBundle", Locale.getDefault());

        mainUser = new User(prop);
        catalogue = new LinkedList<>();
        pantry = new LinkedList<>();

        /**
         * We fetch the products from the database using the gestock api. Created /w Symfony 2.
         * The integration of MySQL-Java wasn't possible because the CIPC's db didn't work.
         */
        try {
            System.out.print("Fetching database content... ");
            launcher.setText(messages.getString("internetProducts"));
            Curl curl = new Curl("http://gestock.xaic.cat/api/baseproducts");
            curl.run();
            JSONArray baseProductsArray = new JSONArray(curl.getResponse());
            launcher.setText(messages.getString("addingProductsCatalogue"));
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
        BaseProduct b = new MilkBaseProduct(0, "Milk 1.5L", "", "", "Candia", new HashMap<>(), 1, 1);
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

        File configFile = new File(filepath + fs + configProperties);

        return configFile.exists() && !configFile.isDirectory();
    }

    private Properties getProperties(String configProperties) throws IOException {
        FileInputStream inputStream = null;
        Properties prop = new Properties();
        try {
//            String propFileName = "config.properties";

            inputStream = new FileInputStream(configProperties);

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
            outputStream = new FileOutputStream(filepath + fs + configProperties);
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

    public void addToPantry(BoughtProduct bp) {
        this.pantry.add(bp);
        setChanged();
        notifyObservers(Constants.OBSERVER_PANTRY_PRODUCT_CREATED);
    }
}