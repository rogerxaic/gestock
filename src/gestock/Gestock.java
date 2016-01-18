/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.controller.GestockController;
import gestock.entity.*;
import gestock.resources.views.components.Launcher;
import gestock.util.Constants;
import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

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
    private String tempPath = (Tools.isWindows() ? System.getProperty("user.home") + fs + "AppData" + fs + "Local" + fs + "Temp" : fs + "tmp") + fs + "gestock";
    private String configProperties = "config.properties";
    private String baseFile = "baseproducts";
    private String boughtFile = "boughtproducts";
    private String shopsFile = "shops";
    private String shoppingPath = "shoppinglists";
    private LinkedList<BaseProduct> catalogue;
    private List<BoughtProduct> pantry;
    private List<Shop> shops;

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
        File configFile = new File(filepath + fs + configProperties);

        init();
        if (!configFile.exists() || configFile.isDirectory()) {
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
        shops = new LinkedList<>();

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
                    JSONObject jsonProduct = (JSONObject) baseProductsArray.get(i);
                    int type = jsonProduct.getInt("type");
                    BaseProduct product;
                    switch (type) {
                        case 0:
                            product = new BaseProduct(jsonProduct);
                            break;
                        case 1:
                            product = new DairyBaseProduct(jsonProduct);
                            break;
                        case 2:
                            product = new MilkBaseProduct(jsonProduct);
                            break;
                        case 3:
                            product = new MeatBaseProduct(jsonProduct);
                            break;
                        case 4:
                            product = new FishBaseProduct(jsonProduct);
                            break;
                        default:
                            product = new BaseProduct(jsonProduct);
                            break;
                    }
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

        /**
         * Load the local products.
         */
        //BaseProducts
        baseProductLoader(filepath + fs + baseFile);
        //Shops
        shopLoader(filepath + fs + shopsFile);
        //BaseProducts
        boughtProductLoader(filepath + fs + boughtFile);
        launcher.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Gestock app = new Gestock();

        try {
            if (Tools.isWindows()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (Tools.isMac()) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Gestock");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }

        } catch (Exception evt) {
            evt.printStackTrace();
        }
        EventQueue.invokeLater(() -> new GestockController(app));
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("hola");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        //t.start();
    }

    private HashMap<String, Double> newNutritionFacts(String[] fields) {
        HashMap<String, Double> nutritionFacts = new HashMap<>();
        nutritionFacts.put("Energy", (fields[10].equals("null")) ? 0.0 : Double.parseDouble(fields[10]));
        nutritionFacts.put("Fats", (fields[11].equals("null")) ? 0.0 : Double.parseDouble(fields[11]));
        nutritionFacts.put("Acids", (fields[12].equals("null")) ? 0.0 : Double.parseDouble(fields[12]));
        nutritionFacts.put("Carbohydrates", (fields[13].equals("null")) ? 0.0 : Double.parseDouble(fields[13]));
        nutritionFacts.put("Sugars", (fields[14].equals("null")) ? 0.0 : Double.parseDouble(fields[14]));
        nutritionFacts.put("Fibers", (fields[15].equals("null")) ? 0.0 : Double.parseDouble(fields[15]));
        nutritionFacts.put("Proteins", (fields[16].equals("null")) ? 0.0 : Double.parseDouble(fields[16]));
        nutritionFacts.put("Salt", (fields[17].equals("null")) ? 0.0 : Double.parseDouble(fields[17]));
        return nutritionFacts;
    }

    /**
     * Verifies if the directories exist. In case they don't, it creates them.
     */
    private void init() {
        Tools.createPath(filepath);
        Tools.createPath(filepath + fs + shoppingPath);
        Tools.createPath(tempPath);
    }

    private Properties getProperties(String configProperties) throws IOException {
        FileInputStream inputStream = null;
        Properties prop = new Properties();
        try {
            inputStream = new FileInputStream(configProperties);
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

    public LinkedList<BaseProduct> getCatalogue() {
        return this.catalogue;
    }

    public void addToCatalogue(BaseProduct baseProduct) {
        this.catalogue.add(baseProduct);
        baseProduct.createInDB(mainUser.getCookies());
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

    public void addToShops(Shop shop) {
        this.shops.add(shop);
        setChanged();
        notifyObservers(Constants.OBSERVER_SHOP_CREATED);
    }

    public BoughtProduct findBoughtProductById(int id) {
        for (BoughtProduct bp : pantry) {
            if (bp.getId() == id)
                return bp;
        }
        return null;
    }

    public BaseProduct findBaseProductById(int id) {
        for (BaseProduct bp : catalogue) {
            if (bp.getId() == id)
                return bp;
        }
        return null;
    }

    public BaseProduct findBaseProductByReference(int reference) {
        for (BaseProduct bp : catalogue) {
            if (bp.getReference() == reference)
                return bp;
        }

        return null;
    }

    public String saveBaseProducts() {
        StringBuilder sb = new StringBuilder();
        for (BaseProduct bp : catalogue) {
            if (bp.getReference() == 0) { //No internet product
                /**
                 * Type of product, to process it later.
                 */
                if (bp instanceof MilkBaseProduct) {
                    sb.append(2);
                    sb.append("\t");
                } else if (bp instanceof DairyBaseProduct) {
                    sb.append(1);
                    sb.append("\t");
                } else if (bp instanceof MeatBaseProduct) {
                    sb.append(3);
                    sb.append("\t");
                } else if (bp instanceof FishBaseProduct) {
                    sb.append(4);
                    sb.append("\t");
                } else {
                    sb.append(0);
                    sb.append("\t");
                }
                sb.append(bp.getReference());
                sb.append("\t");
                sb.append(bp.getId());
                sb.append("\t");
                sb.append(bp.getCode());
                sb.append("\t");
                sb.append(Tools.escape(bp.getName()));
                sb.append("\t");
                sb.append(Tools.escape(bp.getDescription()));
                sb.append("\t");
                sb.append(bp.getQuantity().getSaveString());
                sb.append("\t");
                sb.append(bp.getAlert());
                sb.append("\t");
                sb.append(Tools.escape(bp.getTraces()));
                sb.append("\t");
                sb.append(Tools.escape(bp.getBrand()));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Energy"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Fats"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Acids"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Carbohydrates"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Sugars"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Fibers"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Proteins"));
                sb.append("\t");
                sb.append(bp.getNutritionFacts().get("Salt"));

                /**
                 * Adding specific information on product.
                 */
                if (bp instanceof MilkBaseProduct) {
                    MilkBaseProduct specificProduct = (MilkBaseProduct) bp;
                    sb.append("\t");
                    sb.append(specificProduct.getOriginAnimal());
                    sb.append("\t");
                    sb.append(specificProduct.getSkimmed());
                } else if (bp instanceof DairyBaseProduct) {
                    DairyBaseProduct specificProduct = (DairyBaseProduct) bp;
                    sb.append("\t");
                    sb.append(specificProduct.getOriginAnimal());
                } else if (bp instanceof MeatBaseProduct) {
                    MeatBaseProduct specificProduct = (MeatBaseProduct) bp;
                    sb.append("\t");
                    sb.append(specificProduct.isHalal());
                } else if (bp instanceof FishBaseProduct) {
                    FishBaseProduct specificProduct = (FishBaseProduct) bp;
                    sb.append("\t");
                    sb.append(specificProduct.isFresh());
                }

                sb.append(System.getProperty("line.separator"));
            }
        }
        return (sb.toString());
    }

    public String saveBoughtProducts() {
        StringBuilder sb = new StringBuilder();
        for (BoughtProduct bp : pantry) {

            sb.append(bp.getId());
            sb.append("\t");
            sb.append(bp.getExpirationDay().getTime());
            sb.append("\t");
            sb.append(bp.getBoughtDay().getTime());
            sb.append("\t");
            sb.append(bp.getQuantity());
            sb.append("\t");
            sb.append(bp.getRemainingQuantity());
            sb.append("\t");
            sb.append(bp.getBaseProduct().getId());
            sb.append("\t");
            sb.append(bp.getBaseProduct().getReference());
            sb.append("\t");
            sb.append(bp.getPrice());

            sb.append(System.getProperty("line.separator"));

        }
        return (sb.toString());
    }

    public String saveShops() {
        StringBuilder sb = new StringBuilder();
        for (Shop s : shops) {

            sb.append(s.getId());
            sb.append("\t");
            sb.append(Tools.escape(s.getName()));
            sb.append("\t");
            sb.append(Tools.escape(s.getLocation()));
            sb.append("\t");
            sb.append((s.getUrl() != null) ? Tools.escape(s.getUrl()) : "0");

            sb.append(System.getProperty("line.separator"));

        }
        return (sb.toString());
    }

    public void save(String path) {
        /**
         * Save BaseProducts
         */
        Tools.saver(path + fs + baseFile, saveBaseProducts());

        /**
         * Save shops
         */
        Tools.saver(path + fs + shopsFile, saveShops());

        /**
         * Save BoughtProducts
         */
        Tools.saver(path + fs + boughtFile, saveBoughtProducts());
    }

    public void save() {
        saveProperties();
        save(filepath);
    }

    public void saveTemp() {
        save(tempPath);
    }


    public String getTemp() {
        return tempPath;
    }

    public void baseProductLoader(String file) {
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach((k) -> {
                if (k.length() > 10) {
                    BaseProduct product;
                    String[] fields = k.split("\t");

                    if (!fields[5].equals("")) {
                        fields[5] = fields[5].replaceAll("\\\\n", System.lineSeparator()).replaceAll("\\\\t", "\t");
                    }

                    HashMap<String, Double> nutritionFacts = newNutritionFacts(fields);

                    char type = k.charAt(0);
                    switch (type) {
                        case '0': //BaseProduct
                            product = new BaseProduct(fields, nutritionFacts);
                            catalogue.add(product);
                            break;
                        case '1': //DairyBaseProduct
                            product = new DairyBaseProduct(fields, nutritionFacts);
                            catalogue.add(product);
                            break;
                        case '2': //MilkBaseProduct
                            product = new MilkBaseProduct(fields, nutritionFacts);
                            catalogue.add(product);
                            break;
                        case '3': //MeatBaseProduct
                            product = new MeatBaseProduct(fields, nutritionFacts);
                            catalogue.add(product);
                            break;
                        case '4': //FishBaseProduct
                            product = new FishBaseProduct(fields, nutritionFacts);
                            catalogue.add(product);
                            break;
                        default: //Not a product
                            break;
                    }
                }
            });

        } catch (Exception ignored) {
        }
    }

    public void boughtProductLoader(String file) {
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach((k) -> {
                if (k.length() > 5) {
                    BoughtProduct product;
                    String[] fields = k.split("\t");
                    BaseProduct bp;
                    if (Integer.parseInt(fields[5]) == 0) {
                        bp = findBaseProductByReference(Integer.parseInt(fields[6]));
                    } else {
                        bp = findBaseProductById(Integer.parseInt(fields[5]));
                    }
                    product = new BoughtProduct(bp, fields);
                    pantry.add(product);
                }
            });

        } catch (Exception ignored) {
        }
    }

    public void shopLoader(String file) {
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach((k) -> {
                if (k.length() > 5) {
                    Shop shop;
                    String[] fields = k.split("\t");
                    shop = new Shop(fields);
                    this.shops.add(shop);
                }
            });
        } catch (Exception ignored) {
        }
    }

    public List<Shop> getShops() {
        return this.shops;
    }
}