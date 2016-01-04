package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.resources.views.BaseProductSearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

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
        List<BaseProduct> l = new LinkedList<>();

        LinkedList catalogue = model.getCatalogue();
        for (Object p : catalogue) {
            BaseProduct bp = (BaseProduct) p;
            if (bp.getName().contains(search)) {
                l.add(bp);
            } else if (bp.getDescription().contains(search)) {
                l.add(bp);
            }
        }

        return l;
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
        List<BaseProduct> l = this.getSearchResults(view.getSearchTerm());
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
