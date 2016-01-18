package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.entity.BoughtProduct;
import gestock.entity.ShoppingList;
import gestock.resources.views.ShoppingListView;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Roger on 1/2/2016.
 */
public class ShoppingListController implements Observer {


    private Gestock model;
    private ShoppingListView view;

    public ShoppingListController(Gestock app) {
        this(app, new ShoppingList());
    }

    public ShoppingListController(Gestock app, ShoppingList s) {
        this.model = app;
        view = new ShoppingListView(model, this, s);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BaseProduct) {
            BaseProduct bp = (BaseProduct) arg;
            view.addToShoppingList(bp);
            view.clearSearchField();
            view.fill();
            view.fillSuggestions(suggestions(view.getShoppingList()));
            view.refresh();
            view.setEnabled(true);
        }
    }

    public List<BaseProduct> suggestions(List<BaseProduct> shoppingList) {
        List<BaseProduct> l = new LinkedList<>();
        //Few products
        fewProducts().forEach(l::add);

        //The products that are often bought with the products in the shopping list
        shoppingList.forEach((k) -> k.getBoughtTogether().forEach((key, v) -> l.add(key)));

        //Remove products already in the shopping list
        shoppingList.forEach(l::remove);
        return l;
    }

    public List<BaseProduct> fewProducts() {
        List<BaseProduct> fewProducts = new LinkedList<>();
        for (BoughtProduct bp : model.getPantry()) {
            BaseProduct baseProduct = bp.getBaseProduct();
            if (baseProduct.getQuantityInPantry() <= baseProduct.getAlert()) {
                fewProducts.add(baseProduct);
            }
        }
        return fewProducts;
    }
}
