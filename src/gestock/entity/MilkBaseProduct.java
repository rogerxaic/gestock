package gestock.entity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class MilkBaseProduct extends DairyBaseProduct {
    protected int skimmed; //0-whole 1-semi-skimmed 2-skim

    public MilkBaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal, int skimmed) {
        super(code, name, description, quantity, alert, traces, brand, nutritionFacts, originAnimal);
        this.skimmed = skimmed;
    }

    public MilkBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts, Integer.parseInt(fields[18]), Integer.parseInt(fields[19]));
    }

    public MilkBaseProduct(JSONObject jsonProduct) throws Exception {
        super(jsonProduct);
        this.skimmed = jsonProduct.getInt("skim");
    }

    public static LinkedList<String> getComboboxContent() {
        LinkedList<String> result = new LinkedList<>();

        result.add("baseproduct.infos.milk.type.whole");
        result.add("baseproduct.infos.milk.type.semi");
        result.add("baseproduct.infos.milk.type.skim");

        return result;
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

    @Override
    public JSONObject getJSON() {
        JSONObject object = super.getJSON();
        object.put("type", 2);
        object.put("skim", this.skimmed);
        return object;
    }
}