package gestock.baseProducts;

import java.util.HashMap;

public class PLSBaseProduct extends BaseProduct {

	private int originAnimal;

	public PLSBaseProduct(int code, String name, String description, String brand, HashMap<String, Double> nutritionFacts, int originAnimal) {
		super(code, name, description, brand, nutritionFacts);
		this.originAnimal = originAnimal;
	}

	public int getOriginAnimal() {
		return this.originAnimal;
	}

	/**
	 * 
	 * @param originAnimal
	 */
	public void setOriginAnimal(int originAnimal) {
		this.originAnimal = originAnimal;
	}

}