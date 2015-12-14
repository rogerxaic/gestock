package gestock.baseProducts;

import java.util.HashMap;

public class MilkBaseProduct extends PLSBaseProduct {
    protected int skimmed; //0-skimmed 1-semi-skimmed 2-

    public MilkBaseProduct(int code, String name, String description, String brand, HashMap<String, Double> nutritionFacts, int originAnimal, int skimmed) {
        super(code, name, description, brand, nutritionFacts, originAnimal);
        this.skimmed = skimmed;
    }
}