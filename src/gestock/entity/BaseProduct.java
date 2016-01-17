/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.entity;

import gestock.util.Constants;
import gestock.util.Curl;
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
    protected Quantity quantity = new Quantity("0u"); //units, grams, millilitres
    protected int alert = 5;
    protected String name = "";
    protected String description = "";
    protected String ingredients = "";
    protected String traces = "";
    protected String brand = "";
    protected Stack<Price> prices = new Stack<>();
    protected HashMap<String, Double> nutritionFacts;
    protected List<BoughtProduct> boughtProducts = new LinkedList<>();
    protected Map<BaseProduct, Integer> boughtTogether = new HashMap<>();

    public BaseProduct() {
        counter++;
        this.id = counter;
        this.nutritionFacts = new HashMap<>();
    }

    public BaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.alert = alert;
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
        this.code = (productJSON.get("code") instanceof Integer) ? ((Integer) productJSON.get("code")).longValue() : (long) productJSON.get("code");
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
        this.quantity = productJSON.has("quantity") ? new Quantity((String) productJSON.get("quantity")) : new Quantity("0u");
    }

    public BaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts);
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
        System.out.println(this.toJSONString());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getLatestPrice() {
        if (!prices.isEmpty()) {
            return this.prices.peek();
        } else {
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

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ' ' + getQuantity();
    }

    public int getQuantityInPantry() {
        int q = 0;
        for (BoughtProduct bp : boughtProducts) {
            q += bp.getRemainingQuantity();
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

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        object.put("code", this.getCode());
        object.put("name", this.getName());
        object.put("description", this.getDescription());
        object.put("brand", this.getBrand());
        object.put("energy", this.getNutritionFacts().get("Energy"));
        object.put("fats", this.getNutritionFacts().get("Fats"));
        object.put("acids", this.getNutritionFacts().get("Acids"));
        object.put("carbohydrates", this.getNutritionFacts().get("Carbohydrates"));
        object.put("sugars", this.getNutritionFacts().get("Sugars"));
        object.put("fibers", this.getNutritionFacts().get("Fibers"));
        object.put("proteins", this.getNutritionFacts().get("Proteins"));
        object.put("salt", this.getNutritionFacts().get("Salt"));
        object.put("quantity", this.getQuantity().toString());
        object.put("traces", this.getTraces());
        object.put("ingredients", this.getIngredients());
        return object;
    }

    public JSONObject getCompleteJSON() {
        JSONObject object = this.getJSON();
        object.put("id", this.getReference());
        return object;
    }

    public String toJSONString() {
        return this.getJSON().toString();
    }

    public String getIngredients() {
        return ingredients;
    }

    public void createInDB(String session) {
        Thread t = new Thread(() -> {
            String url = "http://gestock.xaic.cat/api/baseproducts";
            Curl c = (session == null) ? new Curl(url) : new Curl(url, session);
            c.setRequestMethod("POST");
            c.getProperties().put("Content-Type", "application/json; charset=utf-8");
            c.getProperties().put("Accept-Encoding", "gzip, deflate, compress");
            c.getProperties().put("Accept", "application/json");
            c.setPostParameters(this.toJSONString());
            try {
                c.run();
                System.out.println(c.getResponseCode());
                System.out.println(c.getResponse());
                if (c.getResponseCode() == 201) {
                    JSONObject response = new JSONObject(c.getResponse());
                    Integer i = (Integer) response.get("id");
                    this.reference = i.longValue();
                    this.id = 0;
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });
        t.start();
    }
}
