/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window.catalogue;

import gestock.Gestock;
import gestock.entity.*;
import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONObject;

import javax.imageio.ImageIO;
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
public class Product extends JFrame {

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


    public Product(Gestock app, BaseProduct baseProduct, boolean update) {
        super("Gestock - Product");
        this.app = app;
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        codeName = new JTextField(String.valueOf(baseProduct.getCode()), 20);
        lookup = new JButton("Internet lookup");
        lookup.addActionListener(new ButtonHandler());

        LinkedList<ProductType> llpt = new LinkedList<>();
        llpt.add(new ProductType("Normal", "normal"));
        llpt.add(new ProductType("Poisson", "fish"));
        llpt.add(new ProductType("Viande", "meat"));
        llpt.add(new ProductType("Produit Lacté", "DPS")); //PLS Produits Lactés, Dairy Products
        llpt.add(new ProductType("Lait", "milk"));


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
                            "Informations spécifiques du poisson"));
                    break;
                case "meat":
                    specificPanel.add(halal);
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            "Informations spécifiques de la viande"));
                    break;
                case "dps":
                    specificPanel.add(new JButton("dps"));
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            "Informations spécifiques des PLS"));
                    break;
                case "milk":
                    specificPanel.add(new JButton("milk"));
                    specificPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                            "Informations spécifiques du lait"));
                    break;
                default:
                    specificPanel.setBorder(null);
                    break;
            }
            refresh();
        });
        if (update) {
            productType.setEnabled(false);
            if (baseProduct instanceof FishBaseProduct) {
                productType.setSelectedItem(new ProductType("", "fish"));
            } else if (baseProduct instanceof MeatBaseProduct) {
                productType.setSelectedItem(new ProductType("", "meat"));
            } else if (baseProduct instanceof MilkBaseProduct) {
                productType.setSelectedItem(new ProductType("", "milk"));
            } else if (baseProduct instanceof DairyBaseProduct) {
                productType.setSelectedItem(new ProductType("", "DPS"));
            }

        }
        headPanel = new JPanel(new FlowLayout());
        headPanel.add(new JLabel("Type"));
        headPanel.add(productType);
        headPanel.add(new JLabel("Codi barres"));
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
        energieLabel = new JLabel("Energie");
        grassesLabel = new JLabel("Matières grasses");
        acidesLabel = new JLabel("dont acides gras satures");
        glucidesLabel = new JLabel("Glucides");
        sucresLabel = new JLabel("dont sucres");
        fibresLabel = new JLabel("Fibres alimentaires");
        proteinesLabel = new JLabel("Proteines");
        saltLabel = new JLabel("Sel - sodium");
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
                "Informations nutritionnelles - (pour 100 g/ 100 ml)"));
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
            nameLabel = new JLabel("Name");
            descriptionLabel = new JLabel("Description");
            tracesLabel = new JLabel("Traces");
            brandLabel = new JLabel("Brand");
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

        JButton add = new JButton((update) ? "Mettre à jour" : "Ajouter");
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
            if (update) {
                baseProduct.setCode(code);
                baseProduct.setName(nameField.getText());
                baseProduct.setDescription(descriptionArea.getText());
                baseProduct.setBrand(brandField.getText());
                baseProduct.setTraces(tracesField.getText());
                baseProduct.setNutritionFacts(nutritionHashMap);
            } else {
                app.addToCatalogue(baseProduct);
            }
        });
        addPanel = new JPanel(new FlowLayout());
        addPanel.add(add);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(addPanel, BorderLayout.SOUTH);

        try {
            Image img = ImageIO.read(getClass().getResource("../../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void refresh() {
        revalidate();
        repaint();
        pack();
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
                        nameField.setText((String) productArray.get("product_name"));
                        brandField.setText((String) productArray.get("brands"));
                        JSONObject nutriments = (JSONObject) productArray.get("nutriments");
                        energieTextField.setText((String) nutriments.get("energy"));
                        grassesTextField.setText((String) nutriments.get("fat"));
                        acidesTextField.setText((String) nutriments.get("saturated-fat"));
                        glucidesTextField.setText((String) nutriments.get("carbohydrates"));
                        sucresTextField.setText((String) nutriments.get("sugars"));
                        fibresTextField.setText((String) nutriments.get("fiber"));
                        proteinesTextField.setText((String) nutriments.get("proteins"));
                        saltTextField.setText((String) nutriments.get("salt"));
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