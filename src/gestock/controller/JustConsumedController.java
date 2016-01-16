package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.resources.views.JustConsumedView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roger on 1/16/2016.
 */
public class JustConsumedController {

    private Gestock model;
    private JustConsumedView view;

    public JustConsumedController(Gestock app) {
        this.model = app;
        view = new JustConsumedView(model, this);
    }

    public List<BaseProduct> getProductsInPantry() {
        List<BaseProduct> l = new LinkedList<>();

        model.getPantry().stream().filter(boughtProduct -> boughtProduct.getRemainingQuantity() > 0 && !l.contains(boughtProduct.getBaseProduct())).forEach(boughtProduct -> {
            l.add(boughtProduct.getBaseProduct());
        });

        return l;
    }

    public void consume(BaseProduct baseProduct) {
        if (baseProduct.getQuantityInPantry() > 0) {

        }
    }

    public List<BaseProduct> filterSearch(String filter) {
        List<BaseProduct> l = new LinkedList<>();
        filter = filter.toLowerCase();

        for (BaseProduct baseProduct : getProductsInPantry()) {
            if (baseProduct.getName().toLowerCase().equals(filter) || baseProduct.getDescription().toLowerCase().equals(filter)) {
                l.add(baseProduct);
            }
        }

        return l;
    }
}
