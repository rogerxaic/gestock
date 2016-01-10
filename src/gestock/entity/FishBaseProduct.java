package gestock.entity;

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

    public boolean isFresh() {
        return this.fresh;
    }

    /**
     * @param fresh
     */
    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

}