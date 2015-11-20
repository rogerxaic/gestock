/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import gestock.util.Curl;
import gestock.util.Tools;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * @author Roger
 */
public class ProductWindow extends JFrame {

    private JPanel mainPanel;
    private JPanel headPanel;
    private JPanel centerPanel;
    private JPanel addPanel;
    private JPanel infoPanel;
    private JPanel generalInfoPanel;
    private JPanel nutritionPanel;
    private JButton lookup;
    private JTextField codeName;
    //*************** Nutrition facts
    private JLabel nutritionFactsLabel = new JLabel("Informations Nutritionnelles ");
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


    public ProductWindow() {
        super("Gestock - Product");
        setSize(600, 600);
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
                        System.out.println(productReturn);
                    } else {
                        throw new Exception("Product not found");
                    }
                } catch (Exception e) {
                    System.out.println(e);
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

        centerPanel = new JPanel(new BorderLayout());
        infoPanel = new JPanel(new GridLayout(1, 2));
        generalInfoPanel = new JPanel(new BorderLayout());

        {
            energieLabel = new JLabel("Energie");
            grassesLabel = new JLabel("Matières grasses");
            acidesLabel = new JLabel("dont acides gras satures");
            glucidesLabel = new JLabel("Glucides");
            sucresLabel = new JLabel("dont sucres");
            fibresLabel = new JLabel("Fibres alimentaires");
            proteinesLabel = new JLabel("Proteines");
            saltLabel = new JLabel("Sel - sodium");
            energieTextField = new JTextField(10);
            grassesTextField = new JTextField(10);
            acidesTextField = new JTextField(10);
            glucidesTextField = new JTextField(10);
            sucresTextField = new JTextField(10);
            fibresTextField = new JTextField(10);
            proteinesTextField = new JTextField(10);
            saltTextField = new JTextField(10);
        }
        nutritionPanel = new JPanel();
        GroupLayout layout = new GroupLayout(nutritionPanel);
        //getContentPane().setLayout(layout);
        nutritionPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addComponent(nutritionFactsLabel)
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
                .addComponent(nutritionFactsLabel)
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

        infoPanel.add(generalInfoPanel);
        infoPanel.add(nutritionPanel);
        centerPanel.add(infoPanel, BorderLayout.CENTER);

        addPanel = new JPanel(new FlowLayout());
        addPanel.add(new JButton("Ajouter"));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(addPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    public void refresh() {
        setVisible(true);
    }
}
