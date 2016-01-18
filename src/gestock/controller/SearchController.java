package gestock.controller;

import gestock.Gestock;
import gestock.entity.BoughtProduct;
import gestock.resources.views.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Roger on 1/18/2016.
 */
public class SearchController implements KeyListener, ActionListener {

    private Gestock model;
    private SearchView view;

    public SearchController(Gestock model) {
        this.model = model;
        this.view = new SearchView(model, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        view.fillBaseList(model.searchBaseProducts(view.getSearchTerm()));
        view.fillBoughtList(getBoughtProducts(view.getFilter()));
    }

    public List<BoughtProduct> getBoughtProducts(boolean filter) {
        List<BoughtProduct> l = model.searchBoughtProducts(view.getSearchTerm());
        if (filter) {
            l = l.stream().filter(boughtProduct -> boughtProduct.getRemainingQuantity() > 0).collect(Collectors.toList());
        }
        return l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.fillBoughtList(getBoughtProducts(view.getFilter()));
    }
}
