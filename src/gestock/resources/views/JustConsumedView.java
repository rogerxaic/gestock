package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.JustConsumedController;
import gestock.entity.BaseProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roger on 1/16/2016.
 */
public class JustConsumedView extends GFrame {

    private final Gestock model;
    private final JustConsumedController controller;
    private JPanel main;
    private JPanel search;
    private JPanel list;
    private JTextField searchField;

    private HashMap<JCheckBox, BaseProduct> relation;

    public JustConsumedView(Gestock app, JustConsumedController justConsumedController) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("consume.title"));
        setSize(600, 700);

        this.model = app;
        this.controller = justConsumedController;
        this.relation = new HashMap<>();

        searchField = new JTextField();
        search = new JPanel(new BorderLayout());
        search.add(searchField, BorderLayout.NORTH);

        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));

        main = new JPanel(new GridLayout(1, 2));
        main.add(search);
        main.add(list);

        fillSearch(controller.getProductsInPantry());

        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fillList(List<BaseProduct> baseProducts) {
        list.removeAll();
        relation.forEach((k, v) -> {
            this.list.add(k);
        });
    }

    public void fillSearch(List<BaseProduct> baseProducts) {
        search.removeAll();
        for (BaseProduct baseProduct : baseProducts) {
            String text = baseProduct.toString() + " : " + baseProduct.getQuantityInPantry();
            JButton b = new JButton(text);
            b.addActionListener((ActionEvent ae) -> {
                JCheckBox cb = new JCheckBox(text);
                relation.put(cb, baseProduct);
                searchField.setText("");
                search.removeAll();
                refresh();
            });
            search.add(b);
        }
    }
}
