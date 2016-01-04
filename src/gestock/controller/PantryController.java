package gestock.controller;

import gestock.Gestock;
import gestock.entity.BoughtProduct;
import gestock.resources.views.PantryView;

import java.util.List;

/**
 * Created by Roger on 1/2/2016.
 */
public class PantryController {

    private Gestock model;

    public PantryController(Gestock app) {
        this.model = app;
        List<BoughtProduct> pantry = getPantry();
        new PantryView(model, this, pantry);
    }

    public List<BoughtProduct> getPantry() {
        return model.getPantry();
    }
}
