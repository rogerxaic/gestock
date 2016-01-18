package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.entity.ShoppingList;
import gestock.resources.views.GestockView;
import gestock.util.Constants;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.util.*;

/**
 * Created by Roger on 1/8/2016.
 */
public class GestockController implements Observer, ListSelectionListener {
    private Gestock model;
    private GestockView view;

    private LinkedList<BaseProduct> l;

    public GestockController(Gestock model) {
        this.model = model;
        this.view = new GestockView(this.model, this, expireSoonProducts(), fewProducts());
        this.model.addObserver(this);
        this.l = new LinkedList<>();
    }

    public List<BaseProduct> fewProducts() {
        List<BaseProduct> fewProducts = new LinkedList<>();
        for (BoughtProduct bp : model.getPantry()) {
            BaseProduct baseProduct = bp.getBaseProduct();
            if (baseProduct.getQuantityInPantry() <= baseProduct.getAlert()) {
                fewProducts.add(baseProduct);
            }
        }
        return fewProducts;
    }

    public List<BaseProduct> expireSoonProducts() {
        List<BaseProduct> expireSoon = new LinkedList<>();
        for (BoughtProduct bp : model.getPantry()) {
            Date now = new Date();
            long expiry = bp.getExpirationDay().getTime() - now.getTime();
            if (bp.getRemainingQuantity() > 0 && expiry < 7 * 24 * 60 * 60 * 1000) {
                BaseProduct baseProduct = bp.getBaseProduct();
                expireSoon.add(baseProduct);
            }
        }
        return expireSoon;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof Integer) {
            int ar = (int) arg;
            if (ar == Constants.OBSERVER_PANTRY_PRODUCT_CREATED) {
                view.fillExpireSoon(this.expireSoonProducts());
                view.fillFewProducts(this.fewProducts());
                view.refresh();
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selRows;


        if (!e.getValueIsAdjusting()) {
            selRows = view.getTable().getSelectedRows();
            System.out.println(selRows.length);

            if (selRows.length > 0) {
                l = new LinkedList<>();

                TableModel tm = view.getTable().getModel();
                for (int i = 0; i < selRows.length; i++) {
                    BaseProduct b;
                    b = model.searchBaseProducts((String) tm.getValueAt(selRows[i], 0)).get(0);
                    l.add(b);
                }
            }
        }
    }

    public LinkedList<BaseProduct> getL() {
        return l;
    }
}
