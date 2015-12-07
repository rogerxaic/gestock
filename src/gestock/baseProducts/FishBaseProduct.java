package gestock.baseProducts;

import java.util.HashMap;

public class FishBaseProduct extends BaseProduct {

	private boolean fresh;

	public FishBaseProduct(int code, String name, String description, String brand, HashMap<String, Double> nutritionFacts, boolean fresh) {
		super(code, name, description, brand, nutritionFacts);
		this.fresh = fresh;
	}

	public boolean isFresh() {
		return this.fresh;
	}

	/**
	 * 
	 * @param fresh
	 */
	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}

}