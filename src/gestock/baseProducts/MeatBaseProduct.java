package gestock.baseProducts;

public class MeatBaseProduct extends BaseProduct {

	private boolean halal;

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