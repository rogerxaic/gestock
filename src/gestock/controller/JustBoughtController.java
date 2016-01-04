package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.resources.views.JustBoughtView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Roger on 1/4/2016.
 */
public class JustBoughtController implements Observer {
    private Gestock model;

    public JustBoughtController(Gestock app) {
        this.model = app;

        new JustBoughtView(model, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BaseProduct) {
            BaseProduct bp = (BaseProduct) arg;
            BoughtProduct boughtProduct = new BoughtProduct(bp);
        }
    }
}
