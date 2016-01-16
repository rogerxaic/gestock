package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.resources.views.GestockView;
import gestock.util.Constants;

import java.util.*;

/**
 * Created by Roger on 1/8/2016.
 */
public class GestockController implements Observer {
    private Gestock model;
    private GestockView view;

    public GestockController(Gestock model) {
        this.model = model;
        this.view = new GestockView(this.model, expireSoonProducts(), fewProducts());
        this.model.addObserver(this);
    }

    public List<BaseProduct> fewProducts() {
        List<BaseProduct> fewProducts = new LinkedList<>();
        for (BoughtProduct bp : model.getPantry()) {
            BaseProduct baseProduct = bp.getBaseProduct();
            if (baseProduct.getQuantityInPantry() <= 2) {
                fewProducts.add(baseProduct);
            }
        }
        return fewProducts;
    }

    public Map<BaseProduct, Integer> expireSoonProducts() {
        Map<BaseProduct, Integer> expireSoon = new HashMap<>();
        for (BoughtProduct bp : model.getPantry()) {
            Date now = new Date();
            long expiry = bp.getExpirationDay().getTime() - now.getTime();
            if (expiry < 7 * 24 * 60 * 60 * 1000) {
                BaseProduct baseProduct = bp.getBaseProduct();
                if (expireSoon.containsKey(baseProduct)) {
                    expireSoon.put(baseProduct, expireSoon.get(baseProduct) + bp.getQuantity());
                } else {
                    expireSoon.put(baseProduct, bp.getQuantity());
                }
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
}
