package gestock.controller;

import gestock.Gestock;
import gestock.resources.views.LoginView;
import gestock.util.Curl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Roger on 12/29/2015.
 */
public class LoginController implements ActionListener {

    private Gestock model;
    private LoginView view;

    public LoginController(Gestock gestock) {
        this.model = gestock;

        view = new LoginView(model, this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Curl init = new Curl("http://gestock.xaic.cat/register/");
        try {
            init.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cookie = init.getCookies();
        Document doc = Jsoup.parse(init.getResponse());
        String token = doc.select("input[id$=token]").first().attr("value");

        String params = "";
        try {
            params = "fos_user_registration_form%5Bemail%5D=" + URLEncoder.encode(view.getEmail(), "UTF-8") +
                    "&fos_user_registration_form%5Busername%5D=" + URLEncoder.encode(view.getEmail(), "UTF-8") +
                    "&fos_user_registration_form%5BplainPassword%5D%5Bfirst%5D=" + URLEncoder.encode(view.getPassword(), "UTF-8") +
                    "&fos_user_registration_form%5BplainPassword%5D%5Bsecond%5D=" + URLEncoder.encode(view.getPassword(), "UTF-8") +
                    "&fos_user_registration_form%5B_token%5D=" + token;
        } catch (UnsupportedEncodingException ignored) {
        }

        Curl register = new Curl("http://gestock.xaic.cat/register/", cookie);
        register.setRedirect(false);
        register.setRequestMethod("POST");
        register.getProperties().put("Content-Type", "application/x-www-form-urlencoded");
        register.getProperties().put("Accept-Encoding", "gzip, deflate");
        register.getProperties().put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        register.setPostParameters(params);

        try {
            register.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document response = Jsoup.parse(register.getResponse());
        Element link = response.select("a").first();
        String linkHref = link.attr("href");
        if (linkHref.equals("/register/confirmed")) {
            model.getUser().setCookie(register.getCookies());
            model.getUser().setEmail(view.getEmail());
            model.getUser().setPassword(view.getPassword());
            model.getUser().setUpdated();
            view.dispose();
        } else {
            System.err.println(model.messages.getString("login.register.error"));
        }
    }
}
