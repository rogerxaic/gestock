/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import java.util.Date;

/**
 * @author Roger
 */
public class Price {

    protected double totalPrice;
    protected Date date;
    protected BoughtProduct boughtProduct;

    public Price(double totalPrice) {
        this(totalPrice, new Date());
    }

    public Price(double totalPrice, Date date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BoughtProduct getBoughtProduct() {
        return boughtProduct;
    }

    public void setBoughtProduct(BoughtProduct boughtProduct) {
        this.boughtProduct = boughtProduct;
    }
}
