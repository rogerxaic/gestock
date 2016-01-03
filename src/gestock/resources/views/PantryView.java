package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.PantryController;
import gestock.entity.BoughtProduct;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Roger on 1/2/2016.
 */
public class PantryView extends GFrame {

    private Gestock model;
    private PantryController controller;
    private List<BoughtProduct> pantry;

    private JPanel main;
    private JScrollPane tablePane;

    public PantryView(Gestock app, PantryController pantryController, List<BoughtProduct> pantry) {
        super("Gestock - Garde-manger");
        setSize(500, 500);
        this.model = app;
        this.controller = pantryController;
        this.pantry = pantry;

        tablePane = new JScrollPane();
        tablePane.getViewport().add(new JButton("egrtvdfvw"));
        main = new JPanel(new BorderLayout());
        main.add(tablePane, BorderLayout.CENTER);

        setContentPane(main);
        setDefaultCloseOperation(GFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
