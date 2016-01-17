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
public class Shop {

    private static int counter = 0;
    protected int id;
    protected String name;
    protected String location;
    protected String url;

    public Shop(String name, String location, String url) {
        init(name, location, url);
        autoSetId();
    }

    public Shop() {
    }

    public void init(String name, String location, String url) {
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

    public int getId() {
        return id;
    }

    public boolean isValid() {
        return name != null;
    }

    @Override
    public String toString() {
        return name;
    }

    public void autoSetId() {
        counter++;
        this.id = counter;
    }
}
