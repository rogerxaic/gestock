/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.baseProducts.BaseProduct;
import java.util.Calendar;
import java.util.LinkedList;

/**
 *
 * @author Roger
 */
public class ShoppingList {
    protected LinkedList<BaseProduct> products;
    protected User user;
    protected boolean sent;
    protected Calendar date;

    public LinkedList<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<BaseProduct> products) {
        this.products = products;
    }
    
    public void addProduct(BaseProduct product) {
        this.products.add(product);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    
    
}
