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
    private JScrollPane searchListPane;
    private JPanel searchList;
    private JPanel list;
    private JPanel footer;
    private JPanel right;
    private JTextField searchField;
    private JButton consume;

    private HashMap<JCheckBox, BaseProduct> relation;

    public JustConsumedView(Gestock app, JustConsumedController justConsumedController) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("consume.title"));
        setSize(600, 700);

        this.model = app;
        this.controller = justConsumedController;
        this.relation = new HashMap<>();

        searchList = new JPanel();
        searchList.setLayout(new BoxLayout(searchList, BoxLayout.PAGE_AXIS));
        searchListPane = new JScrollPane();
        searchListPane.getViewport().add(searchList);

        searchField = new JTextField();
        searchField.addKeyListener(controller);
        search = new JPanel(new BorderLayout());
        search.add(searchField, BorderLayout.NORTH);
        search.add(searchListPane, BorderLayout.CENTER);

        consume = new JButton(model.messages.getString("shop.button.consume"));
        consume.addActionListener((ActionEvent ae) -> {
            relation.forEach((k, v) -> {
                if (k.isSelected()) {
                    v.consume();
                }
                this.dispose();
            });
        });
        footer = new JPanel();
        footer.add(consume);
        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
        right = new JPanel(new BorderLayout());
        right.add(list, BorderLayout.CENTER);
        right.add(footer, BorderLayout.SOUTH);

        main = new JPanel(new GridLayout(1, 2));
        main.add(search);
        main.add(right);

        fillSearch(controller.getProductsInPantry());

        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fillList() {
        list.removeAll();
        relation.forEach((k, v) -> this.list.add(k));
    }

    public void fillSearch(List<BaseProduct> baseProducts) {
        searchList.removeAll();
        for (BaseProduct baseProduct : baseProducts) {
            String text = baseProduct.toString() + " : " + baseProduct.getQuantityInPantry();
            JButton b = new JButton(text);
            b.addActionListener((ActionEvent ae) -> {
                JCheckBox cb = new JCheckBox(text);
                cb.setSelected(true);
                relation.put(cb, baseProduct);
                searchField.setText("");
                searchList.removeAll();

                fillList();

                list.revalidate();
                list.repaint();
                searchList.revalidate();
                searchList.repaint();
            });
            searchList.add(b);
        }
        searchList.revalidate();
        searchList.repaint();
    }

    public String getSearchTerm() {
        return this.searchField.getText();
    }
}
