package gestock.controller;

import com.sun.javaws.exceptions.InvalidArgumentException;
import gestock.Gestock;
import gestock.entity.BoughtProduct;
import gestock.entity.Price;
import gestock.resources.views.BoughtProductView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Created by Roger on 1/5/2016.
 */
public class BoughtProductController extends Observable implements ActionListener {

    private Gestock model;
    private BoughtProduct boughtProduct;
    private BoughtProductView view;

    public BoughtProductController(Gestock app, BoughtProduct boughtProduct) {
        this.model = app;
        this.boughtProduct = boughtProduct;
        view = new BoughtProductView(model, this, this.boughtProduct);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (view.verifyValid()) {
            boughtProduct.setPrice(new Price(view.getPrice(), view.getBoughtOn()));
            boughtProduct.setBoughtDay(view.getBoughtOn());
            boughtProduct.setExpirationDay(view.getExpiry());
            try {
                boughtProduct.setQuantity(view.getQuantity());
            } catch (InvalidArgumentException e1) {
                System.err.println(e1);
            }

            setChanged();
            notifyObservers(boughtProduct);
            view.dispose();
        }
    }
}
