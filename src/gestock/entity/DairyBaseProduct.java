package gestock.entity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class DairyBaseProduct extends BaseProduct {

    protected int originAnimal; //0=cow;1=goat

    public DairyBaseProduct(long code, String name, String description, Quantity quantity, int alert, String traces, String brand, HashMap<String, Double> nutritionFacts, int originAnimal) {
        super(code, name, description, quantity, alert, traces, brand, nutritionFacts);
        this.originAnimal = originAnimal;
    }

    public DairyBaseProduct(String[] fields, HashMap<String, Double> nutritionFacts) {
        this(Long.parseLong(fields[3]), fields[4], fields[5], new Quantity(fields[6]), Integer.parseInt(fields[7]), fields[8], fields[9], nutritionFacts, Integer.parseInt(fields[18]));
    }

    public DairyBaseProduct(JSONObject jsonProduct) throws Exception {
        super(jsonProduct);
        this.originAnimal = jsonProduct.getInt("origin");
    }

    public static LinkedList<String> getComboboxContent() {
        LinkedList<String> result = new LinkedList<>();

        result.add("baseproduct.infos.dps.origin.cow");
        result.add("baseproduct.infos.dps.origin.goat");

        return result;
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

    @Override
    public JSONObject getJSON() {
        JSONObject object = super.getJSON();
        object.put("type", 1);
        object.put("origin", this.originAnimal);
        return object;
    }
}