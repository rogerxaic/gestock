package gestock.entity;

import org.json.JSONObject;

import java.util.HashMap;

public class FishBaseProduct extends BaseProduct {

    private boolean fresh;

    public FishBaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts, boolean fresh) {
        super(code, name, description, quantity, alert, traces, brand, nutritionFacts);
        this.fresh = fresh;
    }

    public FishBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts, Boolean.parseBoolean(fields[18]));
    }

    public FishBaseProduct(JSONObject jsonProduct) throws Exception {
        super(jsonProduct);
        this.fresh = jsonProduct.getBoolean("fresh");
    }

    public boolean isFresh() {
        return this.fresh;
    }

    /**
     * @param fresh
     */
    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = super.getJSON();
        object.put("type", 4);
        object.put("fresh", this.fresh);
        return object;
    }

}