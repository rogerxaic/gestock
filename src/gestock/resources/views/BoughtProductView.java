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
        super(app.messages.getString("boughtproduct.addProduct"));
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
        addButton = new JButton(model.messages.getString("boughtproduct.add"));
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
            String quantity = String.valueOf(boughtProduct.getQuantity());
            quantityField = new JTextField(quantity, 10);
        } catch (Exception e) {
            quantityField = new JTextField(10);
        }

        main = new JPanel();
        main.add(new JLabel(model.messages.getString("boughtproduct.boughtOn")));
        main.add(boughtOnDate);
        main.add(new JLabel(model.messages.getString("boughtproduct.expiry")));
        main.add(expiryDate);
        main.add(new JLabel(model.messages.getString("boughtproduct.price")));
        main.add(priceField);
        main.add(new JLabel(model.messages.getString("boughtproduct.quantity")));
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
            errors.push(model.messages.getString("boughtproduct.setExpiryDate"));
        }
        if (priceField.getText().equals("")) {
            errors.push(model.messages.getString("boughtproduct.setPrice"));
        }
        if (quantityField.getText().equals("")) {
            errors.push(model.messages.getString("boughtproduct.setQuantity"));
        }

        if (errors.isEmpty()) {
            if (expiryDate.getDate().compareTo(boughtOnDate.getDate()) < 0) {
                int info = JOptionPane.showOptionDialog(null, model.messages.getString("boughtproduct.addExpired"),
                        model.messages.getString("boughtproduct.expired"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
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
