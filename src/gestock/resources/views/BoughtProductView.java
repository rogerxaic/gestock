package gestock.resources.views;

import com.toedter.calendar.JDateChooser;
import gestock.Gestock;
import gestock.controller.BoughtProductController;
import gestock.entity.BoughtProduct;
import gestock.entity.Shop;

import javax.swing.*;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Roger on 1/5/2016.
 */
public class BoughtProductView extends GFrame {
    private BoughtProductController controller;
    private Gestock model;

    private JPanel main;
    private JDateChooser boughtOnDate;
    private JDateChooser expiryDate;
    private JDateChooser testDate;
    private JButton addButton;
    private JTextField priceField;
    private JTextField quantityField;
    private JComboBox<Shop> shops;

    public BoughtProductView(Gestock app, BoughtProductController boughtProductController, BoughtProduct boughtProduct) {
        super("Gestock - Ajouter produit");
        this.model = app;
        this.controller = boughtProductController;

        boughtOnDate = new JDateChooser();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testDate = new JDateChooser(true);
        expiryDate = new JDateChooser(true);
        expiryDate.setDate(testDate.getDate());
        addButton = new JButton("Ajouter");
        addButton.addActionListener(controller);
        shops = new JComboBox();
        for (Shop s : model.getShops()) {
            shops.addItem(s);
        }

        try {
            String price = String.valueOf(boughtProduct.getPrice());
            priceField = new JTextField(price, 10);
        } catch (Exception e) {
            priceField = new JTextField(10);
        }
        try {
            String quantity = String.valueOf(boughtProduct.getPrice());
            quantityField = new JTextField(quantity, 10);
        } catch (Exception e) {
            quantityField = new JTextField(10);
        }

        main = new JPanel();
        main.add(new JLabel("Bought on"));
        main.add(boughtOnDate);
        main.add(new JLabel("Expiry"));
        main.add(expiryDate);
        main.add(new JLabel("Price"));
        main.add(priceField);
        main.add(new JLabel("Quantity"));
        main.add(quantityField);
        main.add(new JLabel(model.messages.getString("shop.title")));
        main.add(shops);
        main.add(addButton);

        setContentPane(main);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Date getExpiry() {
        return this.expiryDate.getDate();
    }

    public Date getBoughtOn() {
        return this.boughtOnDate.getDate();
    }

    public double getPrice() {
        return Double.parseDouble(this.priceField.getText());
    }

    public int getQuantity() {
        return Integer.parseInt(this.quantityField.getText());
    }

    public boolean verifyValid() {
        Stack<String> errors = new Stack<>();
        if (expiryDate.getDate().equals(testDate.getDate())) {
            errors.push("You must set an expiry date");
        }
        if (priceField.getText().equals("")) {
            errors.push("You must set a price");
        }
        if (quantityField.getText().equals("")) {
            errors.push("You must set a quantity");
        }

        if (errors.isEmpty()) {
            if (expiryDate.getDate().compareTo(boughtOnDate.getDate()) < 0) {
                int info = JOptionPane.showOptionDialog(null, "You're adding a product who has already expired. Continue?",
                        "Expired product", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                return info == 0;
            } else {
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.ERROR_MESSAGE);
            errors.removeAllElements();
            return false;
        }
    }

    public Shop getShop() {
        return (Shop) shops.getSelectedItem();
    }
}
