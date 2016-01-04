package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.BaseProductSearchController;
import gestock.entity.BaseProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by Roger on 1/4/2016.
 */
public class BaseProductSearchView extends GFrame {

    private BaseProductSearchController controller;
    private Gestock model;

    private JPanel main;
    private JPanel content;
    private JTextField searchField;

    public BaseProductSearchView(Gestock app, BaseProductSearchController baseProductSearchController) {
        super("Gestock - Chercher produit dans le catalogue");
        this.model = app;
        this.controller = baseProductSearchController;
        setSize(300, 600);

        searchField = new JTextField(20);
        searchField.addKeyListener(controller);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        main = new JPanel(new BorderLayout());
        main.add(searchField, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);

        setResizable(false);
        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getSearchTerm() {
        return searchField.getText();
    }

    public void fill(List<BaseProduct> l) {
        content.removeAll();
        for (Object o : l) {
            BaseProduct bp = (BaseProduct) o;
            JButton b = new JButton(bp.getName());
            b.addActionListener((ActionEvent ae) -> {

            });
            content.add(b);
        }
    }

}
