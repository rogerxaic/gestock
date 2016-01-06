package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.ShoppingList;
import gestock.resources.views.ShoppingListView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Roger on 1/2/2016.
 */
public class ShoppingListController implements Observer {


    private Gestock model;
    private ShoppingListView view;

    public ShoppingListController(Gestock app) {
        this.model = app;
        view = new ShoppingListView(model, this, new ShoppingList());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BaseProduct) {
            BaseProduct bp = (BaseProduct) arg;
            view.addToShoppingList(bp);
            view.clearSearchField();
            view.fill();
            view.refresh();
            view.setEnabled(true);
        }
    }
}
