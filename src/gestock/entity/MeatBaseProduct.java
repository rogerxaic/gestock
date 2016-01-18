package gestock.entity;

import org.json.JSONObject;

import java.util.HashMap;

public class MeatBaseProduct extends BaseProduct {

    private boolean halal;

    public MeatBaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts, boolean halal) {
        super(code, name, description, quantity, alert, traces, brand, nutritionFacts);
        this.halal = halal;
    }

    public MeatBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts, Boolean.parseBoolean(fields[18]));
    }

    public MeatBaseProduct(JSONObject jsonProduct) throws Exception {
        super(jsonProduct);
        this.halal = jsonProduct.getBoolean("halal");
    }

    public boolean isHalal() {
        return this.halal;
    }

    /**
     * @param halal
     */
    public void setHalal(boolean halal) {
        this.halal = halal;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = super.getJSON();
        object.put("type", 3);
        object.put("halal", this.halal);
        return object;
    }

}