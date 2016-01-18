package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.ShopController;
import gestock.entity.Shop;

import javax.swing.*;

/**
 * Created by Roger on 1/17/2016.
 */
public class ShopView extends GFrame {

    private JPanel main;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField urlField;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel urlLabel;

    private JButton addButton;

    public ShopView(Gestock model, ShopController shopController, Shop shop) {
        super(model.messages.getString("app.title") + " - " + model.messages.getString("shop.title"));
        setSize(250, 400);
        boolean update = shop.isValid();

        nameField = new JTextField(shop.getName(), 20);
        locationField = new JTextField(shop.getLocation(), 20);
        urlField = new JTextField(shop.getUrl(), 20);
        nameLabel = new JLabel(model.messages.getString("shop.label.name"));
        locationLabel = new JLabel(model.messages.getString("shop.label.location"));
        urlLabel = new JLabel(model.messages.getString("shop.label.url"));
        String label = (update) ? model.messages.getString("shop.label.update") : model.messages.getString("shop.label.add");
        addButton = new JButton(label);
        addButton.addActionListener(shopController);

        main = new JPanel();
        main.add(nameLabel);
        main.add(nameField);
        main.add(locationLabel);
        main.add(locationField);
        main.add(urlLabel);
        main.add(urlField);
        main.add(addButton);

        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getLocationText() {
        return locationField.getText();
    }

    public String getUrl() {
        return urlField.getText();
    }
}
