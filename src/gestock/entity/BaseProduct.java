/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Constants;
import org.json.JSONObject;

import java.util.*;

/**
 * @author rmiretgine
 */
public class BaseProduct extends Observable {

    protected static long counter = 0;
    protected long reference; //id in the database
    protected long id;
    protected long code; //barcode
    protected int quantity;
    protected String name = "";
    protected String description = "";
    protected String traces = "";
    protected String brand = "";
    protected Stack<Price> prices = new Stack<>();
    protected HashMap<String, Double> nutritionFacts;
    protected List<BoughtProduct> boughtProducts = new LinkedList<>();

    public BaseProduct() {
        counter++;
        this.id = counter;
        this.nutritionFacts = new HashMap<>();
    }

    public BaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.traces = traces;
        this.brand = brand;
        this.nutritionFacts = nutritionFacts;
        this.reference = 0;
        counter++;
        this.id = counter;
    }

    public BaseProduct(Object JSON) throws Exception {
        this.id = 0;
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

        this.reference = (productJSON.get("id") instanceof Integer) ? (long) ((int) productJSON.get("id")) : (long) productJSON.get("id");
    }

    public BaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], fields[6], fields[7], nutritionFacts);
        this.reference = Long.parseLong(fields[1]);
        this.id = Long.parseLong(fields[2]);
        counter = id;
    }

    public long getId() {
        return this.id;
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
        if (!prices.isEmpty()) {
            System.out.println("empty stack");
            return this.prices.peek();
        } else {
            System.out.println("new price");
            return new Price(0.0, null);
        }
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

    public long getReference() {
        return reference;
    }

    public String getTraces() {
        return traces;
    }

    public void setTraces(String traces) {
        this.traces = traces;
    }

    public void setUpdated() {
        setChanged();
        notifyObservers(Constants.OBSERVER_PRODUCT_UPDATED);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ' ' + getQuantity() + "u";
    }

    public int getQuantityInPantry() {
        int q = 0;
        for (BoughtProduct bp : boughtProducts) {
            q += bp.getRemanentQuantity();
        }
        return q;
    }

    public BoughtProduct getOldestBoughtProduct() {
        if (!boughtProducts.isEmpty()) {
            BoughtProduct bp = new BoughtProduct(this);
            bp.setExpirationDay(new Date());
            for (BoughtProduct b : boughtProducts) {
                if (b.getExpirationDay().compareTo(bp.getExpirationDay()) < 0)
                    bp = b;
            }
            return bp;
        } else {
            return null;
        }
    }
}
