/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.baseProducts;

import gestock.Price;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author rmiretgine
 */
public class BaseProduct {

    protected long code;
    protected String name;
    protected String description;
    protected String brand;
    protected Stack<Price> prices;
    protected HashMap<String, Double> nutritionFacts;

    public BaseProduct(long code, String name, String description, String brand, HashMap<String, Double> nutritionFacts) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.nutritionFacts = nutritionFacts;
    }

    public BaseProduct(Object JSON) {
        JSONObject productJSON = (JSONObject) JSON;
        try { //code
            this.code = (long) productJSON.get("code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { //name
            this.name = (String) productJSON.get("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { //desc
            this.description = (String) productJSON.get("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { //brand
            this.brand = (String) productJSON.get("brand");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { //nutr

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getCode() {
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
