/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import java.util.LinkedList;

/**
 *
 * @author Roger
 */
public class Shop {

    protected String name;
    protected String location;
    protected String url;
    protected LinkedList<Price> prices;

    public Shop(String name, String location, String url) {
        this.name = name;
        this.location = location;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addPrice(Price price) {
        this.prices.add(price);
    }

}
