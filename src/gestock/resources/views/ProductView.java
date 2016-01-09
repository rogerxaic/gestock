/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.ProductController;
import gestock.entity.*;
import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * @author Roger
 */
public class ProductView extends GFrame {

    protected Gestock app;

    private JPanel mainPanel;
    private JPanel headPanel;
    private JPanel centerPanel;
    private JPanel specificPanel = new JPanel();
    private JPanel addPanel;
    private JPanel infoPanel;
    private JPanel generalInfoPanel;
    private JPanel nutritionPanel = new JPanel();
    private JButton lookup;
    private JTextField codeName;
    private JComboBox<Object> productType;
    //*************** General product info
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel tracesLabel;
    private JLabel brandLabel;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField tracesField;
    private JTextField brandField;
    //*************** Nutrition facts
    private JLabel energieLabel;
    private JLabel grassesLabel;
    private JLabel acidesLabel;
    private JLabel glucidesLabel;
    private JLabel sucresLabel;
    private JLabel fibresLabel;
    private JLabel proteinesLabel;
    private JLabel saltLabel;
    private JTextField energieTextField;
    private JTextField grassesTextField;
    private JTextField acidesTextField;
    private JTextField glucidesTextField;
    private JTextField sucresTextField;
    private JTextField fibresTextField;
    private JTextField proteinesTextField;
    private JTextField saltTextField;
    //*************** Specific information
    private JCheckBox halal = new JCheckBox("Halal");

    //ProductView View
    private ProductController controller;
    private BaseProduct baseProduct;
    private boolean update;


    public ProductView(Gestock app, ProductController productController, BaseProduct baseProd, boolean updt) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("baseproduct.title"));
        this.app = app;
        this.controller = productController;
        this.baseProduct = baseProd;
        this.update = updt;
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        codeName = new JTextField(String.valueOf(baseProduct.getCode()), 20);
        lookup = new JButton(app.messages.getString("baseproduct.internetlookup"));
        lookup.addActionListener(new ButtonHandler());

        LinkedList<ProductType> llpt = new LinkedList<>();
        llpt.add(new ProductType(app.messages.getString("baseproduct.type.normal"), "normal"));
        llpt.add(new ProductType(app.messages.getString("baseproduct.type.fish"), "fish"));
        llpt.add(new ProductType(app.messages.getString("baseproduct.type.meat"), "meat"));
        llpt.add(new ProductType(app.messages.getString("baseproduct.type.dps"), "DPS")); //PLS Produits Lactés, Dairy Products
        llpt.add(new ProductType(app.messages.getString("baseproduct.type.milk"), "milk"));


        productType = new JComboBox<>(llpt.toArray());
        productType.addActionListener((ActionEvent ae) -> {
            String type = ((ProductType) productType.getSelectedItem()).getBackendName().toLowerCase();
            specificPanel.removeAll();
            switch (type) {
                case "normal":
                    //specificPanel.add(new JLabel(), BorderLayout.SOUTH);
                    specificPanel.setBorder(null);
                    break;
                case "fish":
                    specificPanel.add(new JButton("fish"));
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            app.messages.getString("baseproduct.infos.fish")));
                    break;
                case "meat":
                    specificPanel.add(halal);
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            app.messages.getString("baseproduct.infos.meat")));
                    break;
                case "dps":
                    specificPanel.add(new JButton("dps"));
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            app.messages.getString("baseproduct.infos.dps")));
                    break;
                case "milk":
                    specificPanel.add(new JButton("milk"));
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            app.messages.getString("baseproduct.infos.milk")));
                    break;
                default:
                    specificPanel.setBorder(null);
                    break;
            }
            refresh(false);
        });
        if (update) {
            productType.setEnabled(false);
            if (baseProduct instanceof FishBaseProduct) {
                productType.setSelectedItem(new ProductType("", "fish"));
            } else if (baseProduct instanceof MeatBaseProduct) {
                productType.setSelectedItem(new ProductType("", "meat"));
                halal.setSelected(((MeatBaseProduct) baseProduct).isHalal());
            } else if (baseProduct instanceof MilkBaseProduct) {
                productType.setSelectedItem(new ProductType("", "milk"));
            } else if (baseProduct instanceof DairyBaseProduct) {
                productType.setSelectedItem(new ProductType("", "DPS"));
            }

        }
        headPanel = new JPanel(new FlowLayout());
        headPanel.add(new JLabel(app.messages.getString("baseproduct.labels.type")));
        headPanel.add(productType);
        headPanel.add(new JLabel(app.messages.getString("baseproduct.labels.code")));
        headPanel.add(codeName);
        headPanel.add(lookup);


        HashMap<String, Double> nutritionHM = baseProduct.getNutritionFacts();
        double energyOriginal = nutritionHM.getOrDefault("Energy", 0.0);
        double fatsOriginal = nutritionHM.getOrDefault("Fats", 0.0);
        double acidsOriginal = nutritionHM.getOrDefault("Acids", 0.0);
        double carboOriginal = nutritionHM.getOrDefault("Carbohydrates", 0.0);
        double sugarsOriginal = nutritionHM.getOrDefault("Sugars", 0.0);
        double fibersOriginal = nutritionHM.getOrDefault("Fibers", 0.0);
        double proteinsOriginal = nutritionHM.getOrDefault("Proteins", 0.0);
        double saltOriginal = nutritionHM.getOrDefault("Salt", 0.0);
        energieLabel = new JLabel(app.messages.getString("baseproduct.nutrition.energy"));
        grassesLabel = new JLabel(app.messages.getString("baseproduct.nutrition.fats"));
        acidesLabel = new JLabel(app.messages.getString("baseproduct.nutrition.acids"));
        glucidesLabel = new JLabel(app.messages.getString("baseproduct.nutrition.carbohydrates"));
        sucresLabel = new JLabel(app.messages.getString("baseproduct.nutrition.sugars"));
        fibresLabel = new JLabel(app.messages.getString("baseproduct.nutrition.fibers"));
        proteinesLabel = new JLabel(app.messages.getString("baseproduct.nutrition.proteins"));
        saltLabel = new JLabel(app.messages.getString("baseproduct.nutrition.salt"));
        energieTextField = new JTextField(String.valueOf(energyOriginal), 15);
        grassesTextField = new JTextField(String.valueOf(fatsOriginal), 15);
        acidesTextField = new JTextField(String.valueOf(acidsOriginal), 15);
        glucidesTextField = new JTextField(String.valueOf(carboOriginal), 15);
        sucresTextField = new JTextField(String.valueOf(sugarsOriginal), 15);
        fibresTextField = new JTextField(String.valueOf(fibersOriginal), 15);
        proteinesTextField = new JTextField(String.valueOf(proteinsOriginal), 15);
        saltTextField = new JTextField(String.valueOf(saltOriginal), 15);

        GroupLayout layout = new GroupLayout(nutritionPanel);
        //getContentPane().setLayout(layout);
        nutritionPanel.setLayout(layout);
        nutritionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                app.messages.getString("baseproduct.labels.nutritive")));
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(TRAILING)
                                .addComponent(energieLabel)
                                .addComponent(grassesLabel)
                                .addComponent(acidesLabel)
                                .addComponent(glucidesLabel)
                                .addComponent(sucresLabel)
                                .addComponent(fibresLabel)
                                .addComponent(proteinesLabel)
                                .addComponent(saltLabel))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(energieTextField)
                                .addComponent(grassesTextField)
                                .addComponent(acidesTextField)
                                .addComponent(glucidesTextField)
                                .addComponent(sucresTextField)
                                .addComponent(fibresTextField)
                                .addComponent(proteinesTextField)
                                .addComponent(saltTextField)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(energieLabel)
                        .addComponent(energieTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(grassesLabel)
                        .addComponent(grassesTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(acidesLabel)
                        .addComponent(acidesTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(glucidesLabel)
                        .addComponent(glucidesTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(sucresLabel)
                        .addComponent(sucresTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(fibresLabel)
                        .addComponent(fibresTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(proteinesLabel)
                        .addComponent(proteinesTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(saltLabel)
                        .addComponent(saltTextField))
        );

        {
            nameLabel = new JLabel(app.messages.getString("baseproduct.labels.name"));
            descriptionLabel = new JLabel(app.messages.getString("baseproduct.labels.description"));
            tracesLabel = new JLabel(app.messages.getString("baseproduct.labels.traces"));
            brandLabel = new JLabel(app.messages.getString("baseproduct.labels.brand"));
            nameField = new JTextField(baseProduct.getName(), 10);
            descriptionArea = new JTextArea(baseProduct.getDescription());
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            tracesField = new JTextField(baseProduct.getTraces(), 10);
            brandField = new JTextField(baseProduct.getBrand(), 10);
        }
        generalInfoPanel = new JPanel();
        GroupLayout generalLayout = new GroupLayout(generalInfoPanel);
        //getContentPane().setLayout(layout);
        generalInfoPanel.setLayout(generalLayout);
        //generalInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Informations nutritionnelles"));
        generalLayout.setAutoCreateGaps(true);
        generalLayout.setAutoCreateContainerGaps(true);

        generalLayout.setHorizontalGroup(generalLayout.createParallelGroup(LEADING)
                .addGroup(generalLayout.createSequentialGroup()
                        .addGroup(generalLayout.createParallelGroup(TRAILING)
                                .addComponent(nameLabel)
                                .addComponent(descriptionLabel)
                                .addComponent(tracesLabel)
                                .addComponent(brandLabel))
                        .addGroup(generalLayout.createParallelGroup(LEADING)
                                .addComponent(nameField)
                                .addComponent(descriptionArea)
                                .addComponent(tracesField)
                                .addComponent(brandField)))
        );

        generalLayout.setVerticalGroup(generalLayout.createSequentialGroup()
                .addGroup(generalLayout.createParallelGroup(BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addGroup(generalLayout.createParallelGroup(BASELINE)
                        .addComponent(descriptionLabel)
                        .addComponent(descriptionArea))
                .addGroup(generalLayout.createParallelGroup(BASELINE)
                        .addComponent(tracesLabel)
                        .addComponent(tracesField))
                .addGroup(generalLayout.createParallelGroup(BASELINE)
                        .addComponent(brandLabel)
                        .addComponent(brandField))
        );

        infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(generalInfoPanel);
        infoPanel.add(nutritionPanel);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        centerPanel.add(specificPanel, BorderLayout.SOUTH);

        JButton add = new JButton((update) ?
                app.messages.getString("baseproduct.labels.update") : app.messages.getString("baseproduct.labels.add"));
        add.addActionListener((ActionEvent ae) -> {
            String codeBarres = codeName.getText();
            long code = codeBarres.equals("") ? 0 : Long.parseLong(codeBarres.trim());
            String energyString = energieTextField.getText();
            double energy = (energyString.equals("") ? 0 : Double.parseDouble(energyString));
            String fatsString = grassesTextField.getText();
            double fats = fatsString.equals("") ? 0 : Double.parseDouble(fatsString);
            String acidsString = acidesTextField.getText();
            double acids = acidsString.equals("") ? 0 : Double.parseDouble(acidsString);
            String carbohydratesString = glucidesTextField.getText();
            double carbohydrates = carbohydratesString.equals("") ? 0 : Double.parseDouble(carbohydratesString);
            String sugarsString = sucresTextField.getText();
            double sugars = sugarsString.equals("") ? 0 : Double.parseDouble(sugarsString);
            String fibersString = fibresTextField.getText();
            double fibers = fibersString.equals("") ? 0 : Double.parseDouble(fibersString);
            String proteinsString = proteinesTextField.getText();
            double proteins = proteinsString.equals("") ? 0 : Double.parseDouble(proteinsString);
            String saltString = saltTextField.getText();
            double salt = saltString.equals("") ? 0 : Double.parseDouble(saltString);
            HashMap<String, Double> nutritionHashMap = new HashMap<>();
            nutritionHashMap.put("Energy", energy);
            nutritionHashMap.put("Fats", fats);
            nutritionHashMap.put("Acids", acids);
            nutritionHashMap.put("Carbohydrates", carbohydrates);
            nutritionHashMap.put("Sugars", sugars);
            nutritionHashMap.put("Fibers", fibers);
            nutritionHashMap.put("Proteins", proteins);
            nutritionHashMap.put("Salt", salt);
            //BaseProduct baseProduct = new BaseProduct(code, nameField.getText(), descriptionArea.getText(), brandField.getText(), nutritionHashMap);
            String type = ((ProductType) productType.getSelectedItem()).getBackendName().toLowerCase();
            if (update) {
                baseProduct.setCode(code);
                baseProduct.setName(nameField.getText());
                baseProduct.setDescription(descriptionArea.getText());
                baseProduct.setBrand(brandField.getText());
                baseProduct.setTraces(tracesField.getText());
                baseProduct.setNutritionFacts(nutritionHashMap);
                switch (type) {
                    case "normal":
                        break;
                    case "fish":
                        FishBaseProduct fishBaseProduct = (FishBaseProduct) baseProduct;
                        fishBaseProduct.setFresh(true);
                        break;
                    case "meat":
                        MeatBaseProduct meatBaseProduct = (MeatBaseProduct) baseProduct;
                        meatBaseProduct.setHalal(halal.isSelected());
                        break;
                    case "dps":
                        DairyBaseProduct dairyBaseProduct = (DairyBaseProduct) baseProduct;
                        dairyBaseProduct.setOriginAnimal(1);
                        break;
                    case "milk":
                        MilkBaseProduct milkBaseProduct = (MilkBaseProduct) baseProduct;
                        milkBaseProduct.setOriginAnimal(1);
                        milkBaseProduct.setSkimmed(1);
                        break;
                    default:
                        break;
                }
                baseProduct.setUpdated();
            } else {
                BaseProduct finalBaseProduct;
                switch (type) {
                    case "normal":
                        finalBaseProduct = new BaseProduct(code, nameField.getText(), descriptionArea.getText(),
                                tracesField.getText(), brandField.getText(), nutritionHashMap);
                        break;
                    case "fish":
                        finalBaseProduct = new FishBaseProduct(code, nameField.getText(), descriptionArea.getText(),
                                tracesField.getText(), brandField.getText(), nutritionHashMap, true);
                        break;
                    case "meat":
                        finalBaseProduct = new MeatBaseProduct(code, nameField.getText(), descriptionArea.getText(),
                                tracesField.getText(), brandField.getText(), nutritionHashMap, halal.isSelected());
                        break;
                    case "dps":
                        finalBaseProduct = new DairyBaseProduct(code, nameField.getText(), descriptionArea.getText(),
                                tracesField.getText(), brandField.getText(), nutritionHashMap, 1);
                        break;
                    case "milk":
                        finalBaseProduct = new MilkBaseProduct(code, nameField.getText(), descriptionArea.getText(),
                                tracesField.getText(), brandField.getText(), nutritionHashMap, 1, 1);
                        break;
                    default:
                        finalBaseProduct = new BaseProduct();
                        break;
                }
                app.addToCatalogue(finalBaseProduct);
                //new ProductView(app, finalBaseProduct, true);

            }
            dispose();
        });
        addPanel = new JPanel(new FlowLayout());
        addPanel.add(add);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(addPanel, BorderLayout.SOUTH);



        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String codeReturn = codeName.getText();
            if (Tools.isNumeric(codeReturn)) {
                String url = "http://fr.openfoodfacts.org/api/v0/produit/" + codeReturn + ".json";
                Curl httpsReturn = new Curl(url);
                String jsonProduct;
                try {
                    httpsReturn.run();
                    jsonProduct = httpsReturn.getResponse();
                    JSONObject productReturn = new JSONObject(jsonProduct);
                    if (productReturn.get("status_verbose").equals("product found")) {
                        JSONObject productArray = (JSONObject) productReturn.get("product");
                        try {
                            nameField.setText((String) productArray.get("product_name"));
                        } catch (Exception ignored) {
                        }
                        try {
                            brandField.setText((String) productArray.get("brands"));
                        } catch (Exception ignored) {
                        }
                        try {
                            descriptionArea.setText((String) productArray.get("generic_name"));
                        } catch (Exception ignored) {
                        }
                        try {
                            tracesField.setText((String) productArray.get("traces"));
                        } catch (Exception ignored) {
                        }
                        JSONObject nutriments = (JSONObject) productArray.get("nutriments");
                        try {
                            energieTextField.setText((String) nutriments.get("energy"));
                        } catch (Exception ignored) {
                        }
                        try {
                            grassesTextField.setText((String) nutriments.get("fat"));
                        } catch (Exception ignored) {
                        }
                        try {
                            acidesTextField.setText((String) nutriments.get("saturated-fat"));
                        } catch (Exception ignored) {
                        }
                        try {
                            glucidesTextField.setText((String) nutriments.get("carbohydrates"));
                        } catch (Exception ignored) {
                        }
                        try {
                            sucresTextField.setText((String) nutriments.get("sugars"));
                        } catch (Exception ignored) {
                        }
                        try {
                            fibresTextField.setText((String) nutriments.get("fiber"));
                        } catch (Exception ignored) {
                        }
                        try {
                            proteinesTextField.setText((String) nutriments.get("proteins"));
                        } catch (Exception ignored) {
                        }
                        try {
                            saltTextField.setText((String) nutriments.get("salt"));
                        } catch (Exception ignored) {
                        }
                    } else {
                        throw new Exception("Product not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                System.out.println(codeReturn);
            }
        }
    }

}

class ProductType {
    protected String publicName;
    protected String backendName;

    public ProductType(String publicName, String backendName) {
        this.publicName = publicName;
        this.backendName = backendName;
    }

    public String getBackendName() {
        return backendName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductType that = (ProductType) o;

        return backendName.equals(that.backendName);

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return publicName;
    }
}