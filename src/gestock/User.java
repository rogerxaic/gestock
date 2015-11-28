/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author Roger
 */
public class User {
    
    protected String name;
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

    public void update() {
        prop.setProperty("userName", this.name);
        prop.setProperty("userEmail", this.email);
        prop.setProperty("freeUsername", this.mobile.getUser());
        prop.setProperty("freeSecret", this.mobile.getSecret());
    }
}
