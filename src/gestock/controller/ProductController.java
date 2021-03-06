package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.resources.views.ProductView;

import java.util.Observable;

/**
 * Created by Roger on 12/29/2015.
 */
public class ProductController extends Observable {

    private Gestock model;
    private BaseProduct product;

    public ProductController(Gestock gestock, BaseProduct baseProduct) {
        this.model = gestock;
        this.product = baseProduct;
        if (model.getCatalogue().contains(product)) {
            new ProductView(model, this, product, true);
        } else {
            new ProductView(model, this, product, false);
        }
    }

    public void addToCatalogue(BaseProduct baseProduct) {
        model.addToCatalogue(baseProduct);
        setChanged();
        notifyObservers(baseProduct);
    }
}
