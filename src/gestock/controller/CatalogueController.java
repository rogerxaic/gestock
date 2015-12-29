package gestock.controller;

import gestock.Gestock;
import gestock.resources.views.CatalogueView;
import gestock.util.Constants;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Roger on 12/29/2015.
 */
public class CatalogueController implements Observer {

    private Gestock model;
    private CatalogueView catalogueView;

    public CatalogueController(Gestock model) {
        this.model = model;
        this.model.addObserver(this);
        LinkedList products = model.getCatalogue();
        this.catalogueView = new CatalogueView(this.model, this, products);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof Integer) {
            if (arg.equals(Constants.OBSERVER_CREATED_PRODUCT)) {
                catalogueView.fillCatalogue(true);
                catalogueView.refresh();
            }
        }
    }
}
