/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Constants;
import gestock.util.Curl;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Properties;

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
        if (this.cookie != null) prop.setProperty("cookie", this.cookie);
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
        try {
            verify.run();
            return verify.getResponse().equals("true");
        } catch (Exception ignored) {
        }

        return true;
    }

    public void login() {

    }

    public void clearCookies() {
        this.cookie = null;
    }

    public void logout() {
        this.cookie = null;
        this.password = null;
        this.setUpdated();
    }
}
