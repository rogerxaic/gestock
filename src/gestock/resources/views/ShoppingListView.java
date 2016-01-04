package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.ShoppingListController;
import gestock.entity.BaseProduct;
import gestock.entity.Free;
import gestock.entity.ShoppingList;
import gestock.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 1/2/2016.
 */
public class ShoppingListView extends GFrame {


    private ShoppingListController controller;
    private ShoppingList shoppingList;
    private Map shoppingListTitles;
    private Map shoppingListCheckBox;
    private List<JCheckBox> selectedCheckBox;
    private Gestock model;

    private JPanel main;
    private JPanel headerPanel;
    private JButton addButton;
    private JButton searchButton;
    private JTextField searchField;
    private JScrollPane pannierPane;
    private JPanel pannier;
    private JPanel footerPanel;
    private JButton deleteSelection;
    private JButton sendMessage;
    private JButton copyText;
    private JLabel price;

    public ShoppingListView(Gestock app, ShoppingListController shoppingListController, ShoppingList shoppingList) {
        super("Gestock - Liste d'achats");
        setSize(600, 600);
        this.model = app;
        this.controller = shoppingListController;
        this.shoppingList = shoppingList;
        this.shoppingListTitles = new HashMap<Object, String>();
        this.shoppingListCheckBox = new HashMap<JCheckBox, Object>();
        this.selectedCheckBox = new LinkedList<>();

        searchField = new JTextField(20);
        addButton = new JButton("Ajouter");
        addButton.addActionListener((ActionEvent ae) -> {
            if (!searchField.getText().equals("")) {
                addToShoppingList(searchField.getText());
                clearSearchField();
                fill();
                refresh();
                searchField.grabFocus();
            }
        });
        searchButton = new JButton("Chercher");
        searchButton.addActionListener((ActionEvent ae) -> {
            //setEnabled(false);
        });
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
        headerPanel.add(searchField);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(addButton);
        headerPanel.add(searchButton);

        pannier = new JPanel();
        pannier.setLayout(new BoxLayout(pannier, BoxLayout.PAGE_AXIS));
        pannierPane = new JScrollPane();
        pannierPane.getViewport().add(pannier);

        deleteSelection = new JButton("Effacer selection");
        deleteSelection.addActionListener((ActionEvent ae) -> {
            shoppingListCheckBox.forEach((k, v) -> {
                JCheckBox cb = (JCheckBox) k;
                if (cb.isSelected()) {
                    shoppingListTitles.remove(v);
                }
            });
            fill();
            refresh();
        });
        sendMessage = new JButton("Envoyer");
        sendMessage.addActionListener((ActionEvent ae) -> {
            User u = model.getUser();
            Free f = u.getFree();
            if (f.isValid()) {
                f.sendMessage(getText());
            }
        });
        copyText = new JButton("Copier texte");
        copyText.addActionListener((ActionEvent ae) -> {
            StringSelection stringSelection = new StringSelection(getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        price = new JLabel("Prix pannier (approx.) : " + getPrice());
        footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.LINE_AXIS));
        footerPanel.add(deleteSelection);
        footerPanel.add(sendMessage);
        footerPanel.add(copyText);
        footerPanel.add(Box.createHorizontalGlue());
        footerPanel.add(price);

        main = new JPanel(new BorderLayout());
        main.add(headerPanel, BorderLayout.NORTH);
        main.add(pannierPane, BorderLayout.CENTER);
        main.add(footerPanel, BorderLayout.SOUTH);

        setResizable(false);
        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void clearSearchField() {
        this.searchField.setText("");
    }

    public void addToShoppingList(Object o) {
        if (o instanceof String) {
            String str = (String) o;
            shoppingListTitles.put(str, str);
        } else if (o instanceof BaseProduct) {
            BaseProduct bp = (BaseProduct) o;
            shoppingListTitles.put(bp, bp.getName() + " " + bp.getBrand());
        }
        System.out.println(shoppingListTitles);
    }

    public void fill() {
        pannier.removeAll();
        shoppingListCheckBox = new HashMap<JCheckBox, Object>();
        shoppingListTitles.forEach((k, v) -> {
            JCheckBox cb = new JCheckBox((String) v);
            cb.addActionListener((ActionEvent ae) -> {
                if (cb.isSelected()) {
                    selectedCheckBox.add(cb);
                } else {
                    selectedCheckBox.remove(cb);
                }
            });
            shoppingListCheckBox.put(cb, k);
            pannier.add(cb);
        });
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        shoppingListTitles.forEach((k, v) -> {
            sb.append((String) v);
            sb.append("\n");
        });
        if (getPrice() > 0) {
            sb.append("\n");
            sb.append("Prix pannier approx : \n");
            sb.append(String.valueOf(getPrice()));
        }
        return sb.toString();
    }

    public double getPrice() {
        final double[] price = {0.0};
        shoppingListTitles.forEach((k, v) -> {
            if (k instanceof BaseProduct) {
                BaseProduct bp = (BaseProduct) k;
                price[0] += bp.getLatestPrice().getTotalPrice();
            }
        });
        return price[0];
    }

    @Override
    public void refresh() {
        price.setText("Prix pannier (approx.) : " + getPrice());
        super.refresh(true);
        setSize(600, 600);
    }
}