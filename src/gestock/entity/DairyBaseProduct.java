package gestock.entity;

import java.util.HashMap;

public class DairyBaseProduct extends BaseProduct {

    private int originAnimal;

    public DairyBaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal) {
        super(code, name, description, traces, brand, nutritionFacts);
        this.originAnimal = originAnimal;
    }

    public DairyBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], fields[6], fields[7], nutritionFacts, Integer.parseInt(fields[16]));
    }

    public int getOriginAnimal() {
        return this.originAnimal;
    }

    /**
     * @param originAnimal
     */
    public void setOriginAnimal(int originAnimal) {
        this.originAnimal = originAnimal;
    }

}