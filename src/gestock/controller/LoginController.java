package gestock.controller;

import gestock.Gestock;
import gestock.resources.views.LoginView;

/**
 * Created by Roger on 12/29/2015.
 */
public class LoginController {

    private Gestock model;

    public LoginController(Gestock gestock) {
        this.model = gestock;

        new LoginView(model, this);
    }
}
