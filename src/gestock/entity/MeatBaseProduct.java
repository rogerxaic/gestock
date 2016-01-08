package gestock.entity;

import java.util.HashMap;

public class MeatBaseProduct extends BaseProduct {

    private boolean halal;

    public MeatBaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, boolean halal) {
        super(code, name, description, traces, brand, nutritionFacts);
        this.halal = halal;
    }

    public MeatBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], fields[6], fields[7], nutritionFacts, Boolean.parseBoolean(fields[16]));
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

}