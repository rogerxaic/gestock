/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Curl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        String url = null;
        try {
            url = "https://smsapi.free-mobile.fr/sendmsg?user=" + this.username
                    + "&pass=" + this.secret
                    + "&msg=" + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Curl curl = new Curl(url);
        try {
            curl.run();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println("Il y a eu un problème en envoyant le message...");
        }
    }

    public String getUser() {
        return username;
    }

    public String getSecret() {
        return secret;
    }

    public boolean isValid() {
        return !(username.equals("") || secret.equals(""));
    }
}
