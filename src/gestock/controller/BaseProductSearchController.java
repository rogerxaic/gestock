package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.resources.views.BaseProductSearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Created by Roger on 1/4/2016.
 */
public class BaseProductSearchController extends Observable implements KeyListener, ActionListener {

    private Gestock model;
    private BaseProductSearchView view;

    public BaseProductSearchController(Gestock app) {
        this.model = app;

        view = new BaseProductSearchView(model, this);
    }

    public List<BaseProduct> getSearchResults(String search) {
        return model.searchBaseProducts(search);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        getAndFill();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        getAndFill();
    }

    public void getAndFill() {
        getAndFill(view.getSearchTerm());
    }

    public void getAndFill(String search) {
        List<BaseProduct> l = this.getSearchResults(search);
        view.fill(l);
        view.refresh(true);
        view.putOriginalSize();
        view.putInOriginalLocation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BaseProduct bp = view.getObp().get(e.getSource());
        setChanged();
        notifyObservers(bp);
        view.dispose();
    }
}
