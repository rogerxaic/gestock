package gestock.entity;

import java.util.HashMap;

public class MilkBaseProduct extends DairyBaseProduct {
    protected int skimmed; //0-skimmed 1-semi-skimmed 2-

    public MilkBaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal, int skimmed) {
        super(code, name, description, quantity, alert, traces, brand, nutritionFacts, originAnimal);
        this.skimmed = skimmed;
    }

    public MilkBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts, Integer.parseInt(fields[18]), Integer.parseInt(fields[19]));
    }

    public int getSkimmed() {
        return skimmed;
    }

    public void setSkimmed(int skimmed) {
        this.skimmed = skimmed;
    }

    @Override
    public String toString() {
        return name + ' ' + quantity.getQuantity("L") + "L";
    }
}