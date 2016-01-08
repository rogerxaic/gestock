package gestock.entity;

import java.util.HashMap;

public class MilkBaseProduct extends DairyBaseProduct {
    protected int skimmed; //0-skimmed 1-semi-skimmed 2-

    public MilkBaseProduct(long code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal, int skimmed) {
        super(code, name, description, traces, brand, nutritionFacts, originAnimal);
        this.skimmed = skimmed;
    }

    public MilkBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], fields[6], fields[7], nutritionFacts, Integer.parseInt(fields[16]), Integer.parseInt(fields[17]));
    }

    public int getSkimmed() {
        return skimmed;
    }

    public void setSkimmed(int skimmed) {
        this.skimmed = skimmed;
    }

    @Override
    public String toString() {
        return name + ' ' + getQuantity() / 1000 + "L";
    }
}