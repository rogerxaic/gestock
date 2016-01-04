package gestock.controller;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.resources.views.BaseProductSearchView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roger on 1/4/2016.
 */
public class BaseProductSearchController implements KeyListener {

    private Gestock model;
    private BaseProductSearchView view;

    public BaseProductSearchController(Gestock app) {
        this.model = app;

        view = new BaseProductSearchView(model, this);
    }

    public List getSearchResults(String search) {
        List l = new LinkedList();

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
        List l = this.getSearchResults(view.getSearchTerm());
        view.fill(l);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
