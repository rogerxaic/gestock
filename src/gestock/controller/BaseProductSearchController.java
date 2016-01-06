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
        List<BaseProduct> l = new LinkedList<>();
        HashMap<BaseProduct, Integer> results = new HashMap<>();

        search = search.toLowerCase().trim();

        LinkedList catalogue = model.getCatalogue();
        for (Object p : catalogue) {
            BaseProduct bp = (BaseProduct) p;
            results.put(bp, 0);
            if (bp.getName().toLowerCase().contains(search)) {
                results.put(bp, results.get(bp) + 10);
            }
            if (bp.getDescription().toLowerCase().contains(search)) {
                results.put(bp, results.get(bp) + 8);
            }
            if (search.contains(" ")) {
                String[] multipleSearch = search.split(" ");
                for (String s : multipleSearch) {
                    if (bp.getName().toLowerCase().contains(s)) {
                        results.put(bp, results.get(bp) + 2);
                    }
                    if (bp.getDescription().toLowerCase().contains(s)) {
                        results.put(bp, results.get(bp) + 1);
                    }
                }
            }
        }

        List list = new LinkedList<>(results.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        Map<BaseProduct, Integer> result = new LinkedHashMap<>();
        for (Object aList : list) {
            Map.Entry entry = (Map.Entry) aList;
            result.put((BaseProduct) entry.getKey(), (Integer) entry.getValue());
        }

        result.forEach((k, v) -> {
            if (v != 0) {
                l.add(k);
            }
        });

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
