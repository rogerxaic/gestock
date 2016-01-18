/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Constants;
import gestock.util.Curl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;

/**
 * @author Roger
 */
public class User extends Observable {

    protected String name;
    protected String password;
    protected String cookie;
    protected String email;
    protected String country;
    protected String language;
    protected List<ShoppingList> shoppingList;
    protected Free mobile;

    protected Properties prop;

    public User(Properties prop) {
        this.prop = prop;
        this.name = prop.getProperty("user.name", System.getProperty("user.name"));
        this.email = prop.getProperty("user.email", "");
        this.country = prop.getProperty("user.country", System.getProperty("user.country"));
        this.language = prop.getProperty("user.language", System.getProperty("user.language"));
        this.cookie = prop.getProperty("cookie", null);
        this.password = prop.getProperty("password", null);
        this.shoppingList = new LinkedList<>();
        String freeUsername = prop.getProperty("free.username");
        String freeSecret = prop.getProperty("free.secret");
        if (freeUsername != null && freeSecret != null) {
            this.mobile = new Free(freeUsername, freeSecret);
        } else this.mobile = new Free("", "");
        setChanged();
        notifyObservers(Constants.OBSERVER_USER_CREATED);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Free getFree() {
        return mobile;
    }

    public void setFree(Free free) {
        this.mobile = free;
    }

    public void setUpdated() {
        if (this.name != null) prop.setProperty("user.name", this.name);
        if (this.email != null) prop.setProperty("user.email", this.email);
        if (this.password != null) prop.setProperty("password", this.password);
        if (this.cookie != null) {
            prop.setProperty("cookie", this.cookie);
        } else {
            prop.setProperty("cookie", "");
        }
        if (this.country != null) prop.setProperty("user.country", this.country);
        if (this.language != null) prop.setProperty("user.language", this.language);
        if (this.mobile.isValid()) prop.setProperty("free.username", this.mobile.getUser());
        if (this.mobile.isValid()) prop.setProperty("free.secret", this.mobile.getSecret());
        setChanged();
        notifyObservers(Constants.OBSERVER_USER_UPDATED);
    }

    public boolean isLoggedIn() {
        if (cookie == null) return false;
        Curl verify = new Curl("http://gestock.xaic.cat/user/verify", cookie);
        verify.getProperties().put("Accept-Encoding", "gzip, deflate, sdch");
        verify.getProperties().put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        try {
            verify.run();
            return verify.getResponse().equals("true");
        } catch (Exception ignored) {
        }

        return true;
    }

    public void login() {
        Curl init = new Curl("http://gestock.xaic.cat/login");
        try {
            init.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cookie = init.getCookies();
        Document doc = Jsoup.parse(init.getResponse());
        String token = doc.select("input").first().attr("value");

        String params = "_csrf_token=" + token +
                "&_username=" + email +
                "&_password=" + password +
                "&_remember_me=on" +
                "&_submit=security.login.submit";

        Curl login = new Curl("http://gestock.xaic.cat/login_check", cookie);
        login.setRedirect(false);
        login.setRequestMethod("POST");
        login.getProperties().put("Content-Type", "application/x-www-form-urlencoded");
        login.getProperties().put("Accept-Encoding", "gzip, deflate");
        login.getProperties().put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        login.setPostParameters(params);

        try {
            login.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cookie = login.getCookies();
    }

    public void clearCookies() {
        this.cookie = null;
    }

    public void logout() {
        this.cookie = null;
        this.setUpdated();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocale(Locale locale) {
        this.language = locale.getLanguage();
        this.country = locale.getCountry();
    }

    public String getCookies() {
        return cookie;
    }
}
