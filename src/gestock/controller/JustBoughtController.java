package gestock.controller;

import gestock.Gestock;
import gestock.resources.views.JustBoughtView;

/**
 * Created by Roger on 1/4/2016.
 */
public class JustBoughtController {
    private Gestock model;

    public JustBoughtController(Gestock app) {
        this.model = app;

        new JustBoughtView(model, this);
    }
}
