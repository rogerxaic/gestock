package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.PantryController;
import gestock.entity.BoughtProduct;
import gestock.resources.views.components.BoughtProductModel;
import gestock.resources.views.components.CellRenderer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Roger on 1/2/2016.
 */
public class PantryView extends GFrame {

    private JTable table;
    private Gestock model;
    private PantryController controller;
    private List<BoughtProduct> pantry;

    private JPanel main;
    private JScrollPane tablePane;

    public PantryView(Gestock app, PantryController pantryController, List<BoughtProduct> pantry) {
        super(app.messages.getString("pantry.pantry"));
        setSize(700, 300);
        this.model = app;
        this.controller = pantryController;
        this.pantry = pantry;

        CellRenderer cellRenderer = new CellRenderer(model);
        TableModel tableModel = new BoughtProductModel(model, pantry);
        this.table = new JTable(tableModel);
        this.table.setDefaultRenderer(Date.class, cellRenderer);
        this.table.setDefaultRenderer(Integer.class, cellRenderer);
        ListSelectionModel listMod = table.getSelectionModel();
        listMod.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listMod.addListSelectionListener(controller);

        tablePane = new JScrollPane();
        tablePane.getViewport().add(table);
        main = new JPanel(new BorderLayout());
        main.add(tablePane, BorderLayout.CENTER);

        setContentPane(main);
        setDefaultCloseOperation(GFrame.DISPOSE_ON_CLOSE);
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getTable() {
        return table;
    }
}
