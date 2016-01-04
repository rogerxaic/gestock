package gestock.entity;

import java.util.Calendar;

public class BoughtProduct {

    protected User user;
    private Calendar expirationDay;
    private Calendar boughtDay;
    private int quantity;
    private Price price;
    //protected ShoppingList shoppingList;
    private BaseProduct baseProduct;

    public BoughtProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
    }

    public Calendar getExpirationDay() {
        return this.expirationDay;
    }

    public void setExpirationDay(Calendar expirationDay) {
        this.expirationDay = expirationDay;
    }

    public Calendar getBoughtDay() {
        return this.boughtDay;
    }

    public void setBoughtDay(Calendar boughtDay) {
        this.boughtDay = boughtDay;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}
