package gestock.baseProducts;

public class FishBaseProduct extends BaseProduct {

	private boolean fresh;

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