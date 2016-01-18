package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.resources.views.JustConsumedView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Roger on 1/16/2016.
 */
public class JustConsumedController implements KeyListener {

    private Gestock model;
    private JustConsumedView view;

    public JustConsumedController(Gestock app) {
        this.model = app;
        view = new JustConsumedView(model, this);
    }

    public List<BaseProduct> getProductsInPantry() {
        List<BaseProduct> l = new LinkedList<>();

        for (BoughtProduct bp : model.getPantry()) {
            if (bp.getRemainingQuantity() > 0) {
                l.add(bp.getBaseProduct());
            }
        }

        return l;
    }

    public List<BaseProduct> filterSearch(String filter) {
        return model.searchBaseProducts(filter).stream().filter(baseProduct -> baseProduct.getQuantityInPantry()>0).collect(Collectors.toList());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        getAndFill();
    }

    public void getAndFill() {
        getAndFill(view.getSearchTerm());
    }

    public void getAndFill(String search) {
        List<BaseProduct> l = this.filterSearch(search);
        view.fillSearch(l);
    }
}
