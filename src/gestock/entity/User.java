/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Constants;
import gestock.util.Curl;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Properties;

/**
 *
 * @author Roger
 */
public class User extends Observable {
    
    protected String name;
    protected String password;
    protected String cookie;
    protected String email;
    protected LinkedList<ShoppingList> shoppingList;
    protected Free mobile;

    protected Properties prop;

    public User(Properties prop) {
        this.prop = prop;
        String userName = prop.getProperty("userName");
        this.name = (userName != null) ? userName : System.getProperty("user.name");

        String userEmail = prop.getProperty("userEmail");
        this.email = userEmail != null ? userEmail : "";

        String freeUsername = prop.getProperty("freeUsername");
        String freeSecret = prop.getProperty("freeSecret");
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
        if (this.name != null) prop.setProperty("userName", this.name);
        if (this.email != null) prop.setProperty("userEmail", this.email);
        if (this.password != null) prop.setProperty("password", this.password);
        if (this.cookie != null) prop.setProperty("cookie", this.cookie);
        if (this.mobile.isValid()) prop.setProperty("freeUsername", this.mobile.getUser());
        if (this.mobile.isValid()) prop.setProperty("freeSecret", this.mobile.getSecret());
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
