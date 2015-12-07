/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import gestock.Gestock;
import gestock.baseProducts.BaseProduct;
import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * @author Roger
 */
public class ProductWindow extends JFrame {

    protected Gestock app;

    private JPanel mainPanel;
    private JPanel headPanel;
    private JPanel centerPanel;
    private JPanel addPanel;
    private JPanel infoPanel;
    private JPanel generalInfoPanel;
    private JPanel nutritionPanel;
    private JButton lookup;
    private JTextField codeName;
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


    public ProductWindow(Gestock app) {
        super("Gestock - Product");
        this.app = app;
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        codeName = new JTextField(20);
        lookup = new JButton("Internet lookup");
        lookup.addActionListener((ActionEvent ae) -> {
            String codeReturn = codeName.getText();
            if (Tools.isNumeric(codeReturn)) {
                String url = "http://fr.openfoodfacts.org/api/v0/produit/" + codeReturn + ".json";
                Curl httpsReturn = new Curl(url);
                String jsonProduct = "";
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
        });


        headPanel = new JPanel(new FlowLayout());
        headPanel.add(new JLabel("Type"));
        headPanel.add(new JComboBox<>(new String[]{"Normal", "Fish", "Meat", "Produit Lacté", " - Milk"}));
        headPanel.add(new JLabel("Codi barres"));
        headPanel.add(codeName);
        headPanel.add(lookup);



        {
            energieLabel = new JLabel("Energie");
            grassesLabel = new JLabel("Matières grasses");
            acidesLabel = new JLabel("dont acides gras satures");
            glucidesLabel = new JLabel("Glucides");
            sucresLabel = new JLabel("dont sucres");
            fibresLabel = new JLabel("Fibres alimentaires");
            proteinesLabel = new JLabel("Proteines");
            saltLabel = new JLabel("Sel - sodium");
            energieTextField = new JTextField(15);
            grassesTextField = new JTextField(15);
            acidesTextField = new JTextField(15);
            glucidesTextField = new JTextField(15);
            sucresTextField = new JTextField(15);
            fibresTextField = new JTextField(15);
            proteinesTextField = new JTextField(15);
            saltTextField = new JTextField(15);
        }
        nutritionPanel = new JPanel();
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
            nameField = new JTextField(10);
            descriptionArea = new JTextArea();
            tracesField = new JTextField(10);
            brandField = new JTextField(10);
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

        JButton add = new JButton("Ajouter");
        add.addActionListener((ActionEvent ae) -> {
            String codeBarres = codeName.getText();
            int code = codeBarres.equals("") ? 0 : Integer.parseInt(codeBarres);
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
            HashMap<String, Double> nutritionHashMap = new HashMap();
            nutritionHashMap.put("Energy", energy);
            nutritionHashMap.put("Fats", fats);
            nutritionHashMap.put("Acids", acids);
            nutritionHashMap.put("Carbohydrates", carbohydrates);
            nutritionHashMap.put("Sugars", sugars);
            nutritionHashMap.put("Fibers", fibers);
            nutritionHashMap.put("Proteins", proteins);
            nutritionHashMap.put("Salt", salt);
            BaseProduct baseProduct = new BaseProduct(code, nameField.getText(), descriptionArea.getText(), brandField.getText(), nutritionHashMap);
            app.getCatalogue().add(baseProduct);
            System.out.println(app.getCatalogue().toString());
        });
        addPanel = new JPanel(new FlowLayout());
        addPanel.add(add);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(addPanel, BorderLayout.SOUTH);

        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
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
        setVisible(true);
    }
}
