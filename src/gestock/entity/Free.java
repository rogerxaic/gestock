/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

/**
 *
 * @author Roger
 */
public class Free {
    private String username;
    private String secret;

    public Free(String username, String secret) {
        this.username = username;
        this.secret = secret;
    }
    
    public void sendMessage(String message) {
        
    }

    public String getUser() {
        return username;
    }

    public String getSecret() {
        return secret;
    }
}
