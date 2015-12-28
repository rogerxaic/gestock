package gestock.entity;

import java.util.HashMap;

public class MeatBaseProduct extends BaseProduct {

	private boolean halal;

	public MeatBaseProduct(int code, String name, String description, String traces, String brand, HashMap<String, Double> nutritionFacts, boolean halal) {
		super(code, name, description, traces, brand, nutritionFacts);
		this.halal = halal;
    }

	public boolean isHalal() {
		return this.halal;
	}

	/**
	 * 
	 * @param halal
	 */
	public void setHalal(boolean halal) {
		this.halal = halal;
	}

}