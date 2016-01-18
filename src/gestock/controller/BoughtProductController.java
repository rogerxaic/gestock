package gestock.controller;

import gestock.Gestock;
import gestock.entity.BoughtProduct;
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
            boughtProduct.setPrice(view.getPrice());
            boughtProduct.setBoughtDay(view.getBoughtOn());
            boughtProduct.setExpirationDay(view.getExpiry());
            boughtProduct.setShop(view.getShop());
            try {
                boughtProduct.setQuantity(view.getQuantity());
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }

            setChanged();
            notifyObservers(boughtProduct);
            view.dispose();
        }
    }
}
