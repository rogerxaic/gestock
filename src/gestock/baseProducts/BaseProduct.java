/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.baseProducts;

import gestock.Price;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author rmiretgine
 */
public class BaseProduct {

    protected int code;
    protected String name;
    protected String description;
    protected String brand;
    protected Stack<Price> prices;
    protected HashMap<String, Integer> nutritionFacts;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getLatestPrice() {
        return this.prices.peek();
    }

    public double getPriceAverage() {
        double times = 0.0;
        double sum = 0.0;
        for (Price obj : prices) {
            sum += obj.getTotalPrice();
            times++;
        }
        return (times==0.0)?0.0:(sum/times);
    }
    
    public void addPrice(Price price) {
        this.prices.push(price);
    }
}
