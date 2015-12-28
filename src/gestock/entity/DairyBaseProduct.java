package gestock.entity;

import java.util.HashMap;

public class DairyBaseProduct extends BaseProduct {

    private int originAnimal;

    public DairyBaseProduct(int code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal) {
        super(code, name, description, traces, brand, nutritionFacts);
        this.originAnimal = originAnimal;
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