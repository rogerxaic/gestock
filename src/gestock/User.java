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

    public User(Properties prop) {
        String userName = prop.getProperty("userName");
        this.name = (userName != null) ? userName : System.getProperty("user.name");

        String userEmail = prop.getProperty("userEmail");
        if (userEmail != null) this.email = userEmail;

        String freeUsername = prop.getProperty("freeUsername");
        String freeSecret = prop.getProperty("freeSecret");
        if (freeUsername != null && freeSecret != null) {
            this.mobile = new Free(freeUsername, freeSecret);
        }
    }

    public String getName() {
        return name;
    }
}
