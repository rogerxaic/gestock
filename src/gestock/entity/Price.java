/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import java.util.Calendar;

/**
 *
 * @author Roger
 */
public class Price {

    protected double totalPrice;
    protected Calendar date;
    protected BoughtProduct boughtProduct;
    protected Shop shop;

    public Price(double totalPrice, Calendar date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public BoughtProduct getBoughtProduct() {
        return boughtProduct;
    }

    public void setBoughtProduct(BoughtProduct boughtProduct) {
        this.boughtProduct = boughtProduct;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
