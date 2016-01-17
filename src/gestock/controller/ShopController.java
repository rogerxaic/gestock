package gestock.controller;

import gestock.Gestock;
import gestock.entity.Shop;
import gestock.resources.views.ShopView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Roger on 1/17/2016.
 */
public class ShopController implements ActionListener {

    private Gestock model;
    private ShopView view;
    private Shop shop;

    public ShopController(Gestock model, Shop shop) {
        this.model = model;
        this.shop = shop;
        view = new ShopView(model, this, shop);
    }

    public ShopController(Gestock model) {
        this(model, new Shop());
    }

    public void updateShop() {
        String name = view.getName();
        String location = view.getLocationText();
        String url = view.getUrl();

        if (shop.isValid()) { //update
            shop.init(name, location, url);
        } else {
            shop.init(name, location, url);
            shop.autoSetId();
            model.addToShops(shop);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateShop();
        view.dispose();
    }
}
