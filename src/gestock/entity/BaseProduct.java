/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author rmiretgine
 */
public class BaseProduct {

    protected long id; //id in the database
    protected long code; //barcode
    protected String name = "";
    protected String description = "";
    protected String traces = "";
    protected String brand = "";
    protected Stack<Price> prices;
    protected HashMap<String, Double> nutritionFacts;

    public BaseProduct() {
        this.nutritionFacts = new HashMap<>();
    }

    public BaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.traces = traces;
        this.brand = brand;
        this.nutritionFacts = nutritionFacts;
    }

    public BaseProduct(Object JSON) throws Exception {
        JSONObject productJSON = (JSONObject) JSON;
        this.code = (long) productJSON.get("code");
        this.name = (String) productJSON.get("name");
        this.description = (String) productJSON.get("description");
        this.brand = (String) productJSON.get("brand");
        double en = (productJSON.get("energy") instanceof Integer) ? (double) ((int) productJSON.get("energy")) : (double) productJSON.get("energy");
        double fa = (productJSON.get("fats") instanceof Integer) ? (double) ((int) productJSON.get("fats")) : (double) productJSON.get("fats");
        double ac = (productJSON.get("acids") instanceof Integer) ? (double) ((int) productJSON.get("acids")) : (double) productJSON.get("acids");
        double ca = (productJSON.get("carbohydrates") instanceof Integer) ? (double) ((int) productJSON.get("carbohydrates")) : (double) productJSON.get("carbohydrates");
        double su = (productJSON.get("sugars") instanceof Integer) ? (double) ((int) productJSON.get("sugars")) : (double) productJSON.get("sugars");
        double fi = (productJSON.get("fibers") instanceof Integer) ? (double) ((int) productJSON.get("fibers")) : (double) productJSON.get("fibers");
        double pr = (productJSON.get("proteins") instanceof Integer) ? (double) ((int) productJSON.get("proteins")) : (double) productJSON.get("proteins");
        double sa = (productJSON.get("salt") instanceof Integer) ? (double) ((int) productJSON.get("salt")) : (double) productJSON.get("salt");
        this.nutritionFacts = new HashMap<>();
        this.nutritionFacts.put("Energy", en);
        this.nutritionFacts.put("Fats", fa);
        this.nutritionFacts.put("Acids", ac);
        this.nutritionFacts.put("Carbohydrates", ca);
        this.nutritionFacts.put("Sugars", su);
        this.nutritionFacts.put("Fibers", fi);
        this.nutritionFacts.put("Proteins", pr);
        this.nutritionFacts.put("Salt", sa);

        this.id = (productJSON.get("id") instanceof Integer) ? (long) ((int) productJSON.get("id")) : (long) productJSON.get("id");
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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
        return (times == 0.0) ? 0.0 : (sum / times);
    }

    public void addPrice(Price price) {
        this.prices.push(price);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public HashMap<String, Double> getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(HashMap<String, Double> nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTraces() {
        return traces;
    }

    public void setTraces(String traces) {
        this.traces = traces;
    }
}
