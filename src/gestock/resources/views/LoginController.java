package gestock.resources.views;

import gestock.Gestock;

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
