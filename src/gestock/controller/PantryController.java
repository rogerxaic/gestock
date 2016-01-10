package gestock.controller;

import gestock.Gestock;
import gestock.entity.BoughtProduct;
import gestock.resources.views.PantryView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roger on 1/2/2016.
 */
public class PantryController implements ListSelectionListener {

    private Gestock model;
    private PantryView view;

    public PantryController(Gestock app) {
        this.model = app;
        List<BoughtProduct> pantry = getPantry();
        view = new PantryView(model, this, pantry);
    }

    public List<BoughtProduct> getPantry() {
        List<BoughtProduct> pantry = model.getPantry();
        List<BoughtProduct> list = new LinkedList<>();
        for (BoughtProduct bp : pantry) {
            if (bp.getRemainingQuantity() > 0) {
                list.add(bp);
            }
        }
        return list;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selRows;

        if (!e.getValueIsAdjusting()) {
            selRows = view.getTable().getSelectedRows();

            if (selRows.length > 0) {
                TableModel tm = view.getTable().getModel();
                BoughtProduct bp = model.findBoughtProductById((Integer) tm.getValueAt(selRows[0], 0));
                System.out.println(bp);
            }
        }
    }
}
