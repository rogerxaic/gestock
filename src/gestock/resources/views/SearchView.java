package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.BoughtProductController;
import gestock.controller.ProductController;
import gestock.controller.SearchController;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by Roger on 1/18/2016.
 */
public class SearchView extends GFrame {

    private Gestock model;

    private JTabbedPane tabbedPane;
    private JPanel main;
    private JPanel header;
    private JScrollPane catalogueProductsPane;
    private JPanel catalogueProducts;
    private JScrollPane pantryProductsPane;
    private JPanel pantryProducts;
    private JPanel pantry;
    private JTextField searchField;
    private JCheckBox filter;

    public SearchView(Gestock app, SearchController searchController) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("search.title"));
        setSize(700, 500);
        this.model = app;

        searchField = new JTextField(20);
        searchField.addKeyListener(searchController);
        header = new JPanel();
        header.add(new JLabel(app.messages.getString("search.title")));
        header.add(searchField);

        catalogueProducts = new JPanel();
        catalogueProducts.setLayout(new BoxLayout(catalogueProducts, BoxLayout.PAGE_AXIS));
        catalogueProductsPane = new JScrollPane();
        catalogueProductsPane.getViewport().add(catalogueProducts);

        filter = new JCheckBox("En stock");
        filter.addActionListener(searchController);
        pantryProducts = new JPanel();
        pantryProducts.setLayout(new BoxLayout(pantryProducts, BoxLayout.PAGE_AXIS));
        pantryProductsPane = new JScrollPane();
        pantryProductsPane.getViewport().add(pantryProducts);

        pantry = new JPanel(new BorderLayout());
        pantry.add(pantryProductsPane, BorderLayout.CENTER);
        pantry.add(filter, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addTab(app.messages.getString("catalogue.title"), catalogueProductsPane);
        tabbedPane.addTab(app.messages.getString("pantry.title"), pantry);

        main = new JPanel(new BorderLayout());
        main.add(tabbedPane, BorderLayout.CENTER);
        main.add(header, BorderLayout.NORTH);

        setContentPane(main);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getSearchTerm() {
        return this.searchField.getText();
    }

    public void fillBaseList(List<BaseProduct> baseProductList) {
        catalogueProducts.removeAll();
        baseProductList.forEach((k) -> {
            JButton b = new JButton(k.toString());
            b.addActionListener((ActionEvent ae) -> {
                new ProductController(model, k);
            });
            catalogueProducts.add(b);
        });

        catalogueProductsPane.repaint();
        catalogueProductsPane.revalidate();
    }

    public void fillBoughtList(List<BoughtProduct> boughtProductList) {
        pantryProducts.removeAll();
        boughtProductList.forEach((k) -> {
            JButton b = new JButton(k.toString());
            b.addActionListener((ActionEvent ae) -> {
                //new ProductController(model, k);
                new BoughtProductController(model, k);
            });
            pantryProducts.add(b);
        });

        pantryProductsPane.repaint();
        pantryProductsPane.revalidate();
    }

    public boolean getFilter() {
        return filter.isSelected();
    }
}
