package gestock.controller;

import gestock.Gestock;
import gestock.entity.ShoppingList;
import gestock.resources.views.ShoppingListView;

/**
 * Created by Roger on 1/2/2016.
 */
public class ShoppingListController {


    private Gestock model;

    public ShoppingListController(Gestock app) {
        this.model = app;
        new ShoppingListView(model, this, new ShoppingList());
    }
}
