package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.resources.views.JustBoughtView;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Created by Roger on 1/4/2016.
 */
public class JustBoughtController implements Observer {
    private Gestock model;
    private Stack<BoughtProduct> toAdd;
    private JustBoughtView view;

    public JustBoughtController(Gestock app) {
        this.model = app;
        this.toAdd = new Stack<>();
        this.view = new JustBoughtView(model, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BaseProduct) {
            BaseProduct bp = (BaseProduct) arg;
            BoughtProduct boughtProduct = new BoughtProduct(bp);
            boughtProduct.setBaseProduct(bp);
            BoughtProductController boughtProductController = new BoughtProductController(model, boughtProduct);
            boughtProductController.addObserver(this);
        } else if (arg instanceof BoughtProduct) {
            BoughtProduct bp = (BoughtProduct) arg;
            toAdd.push(bp);
            view.fill(toAdd);
        }
    }

    public void deleteAll() {
        toAdd = new Stack<>();
        view.fill(toAdd);
    }

    public void deleteLast() {
        if (!toAdd.isEmpty()) {
            toAdd.pop();
            view.fill(toAdd);
        }
    }

    public void addToPantry() {
        for (BoughtProduct bp : toAdd) {
            model.addToPantry(bp);
        }
        view.dispose();
    }
}
