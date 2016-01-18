package gestock.entity;

import java.util.Date;

public class BoughtProduct {

    private static int counter = 0;
    private int id;
    private Date expirationDay;
    private Date boughtDay;
    private int quantity; //original quantity
    private int remainingQuantity; //original quantity
    private Double price;
    private Shop shop;
    //protected ShoppingList shoppingList;
    private BaseProduct baseProduct;

    public BoughtProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
        baseProduct.boughtProducts.add(this);
        counter++;
        this.id = counter;
    }

    public BoughtProduct(BaseProduct baseProduct, String[] fields) {
        this(baseProduct);
        this.expirationDay = new Date(Long.parseLong(fields[1]));
        this.boughtDay = new Date(Long.parseLong(fields[2]));
        this.quantity = Integer.parseInt(fields[3]);
        this.remainingQuantity = Integer.parseInt(fields[4]);
        this.id = Integer.parseInt(fields[0]);
        counter = id;
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
            this.remainingQuantity = quantity;
        } else {
            throw new Exception("Quantity must be positive");
        }
    }

    public Double getPrice() {
        if (this.price != null) {
            return this.price;
        } else {
            return 0.0;
        }
    }

    public void setPrice(Double price) {
        this.price = price;
        this.baseProduct.addPrice(this.price);
    }

    public BaseProduct getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
    }

    public boolean use() {
        if (this.remainingQuantity > 0) {
            this.remainingQuantity--;
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

    public int getId() {
        return id;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void consume() {
        if (getRemainingQuantity() > 0) {
            this.remainingQuantity--;
        }
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
