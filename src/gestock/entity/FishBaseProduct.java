package gestock.entity;

import java.util.HashMap;

public class FishBaseProduct extends BaseProduct {

    private boolean fresh;

    public FishBaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, boolean fresh) {
        super(code, name, description, traces, brand, nutritionFacts);
        this.fresh = fresh;
    }

    public FishBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], fields[6], fields[7], nutritionFacts, Boolean.parseBoolean(fields[16]));
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