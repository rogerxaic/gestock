package gestock.entity;

import java.util.Date;

public class BoughtProduct {

    protected User user;
    private Date expirationDay;
    private Date boughtDay;
    private int quantity; //original quantity
    private int remanentQuantity; //original quantity
    private Price price;
    //protected ShoppingList shoppingList;
    private BaseProduct baseProduct;

    public BoughtProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
    }

    public Date getExpirationDay() {
        return this.expirationDay;
    }

    public void setExpirationDay(Date expirationDay) {
        this.expirationDay = expirationDay;
    }

    public Date getBoughtDay() {
        return this.boughtDay;
    }

    public void setBoughtDay(Date boughtDay) {
        this.boughtDay = boughtDay;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity >= 0) {
            this.quantity = quantity;
            this.remanentQuantity = quantity;
        } else {
            throw new Exception("Quantity must be positive");
        }
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public BaseProduct getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean use() {
        if (this.remanentQuantity > 0) {
            this.remanentQuantity--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return baseProduct +
                ", quantity:" + quantity;
    }
}
