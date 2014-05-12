package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Order {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("product")
    private List<Product> products;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("total")
    private float total;
    @JsonProperty("buyer")
    private User buyer;


    public Order() {
        products = new ArrayList<Product>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", quantity=" + quantity +
                ", total=" + total +
                ", buyer=" + buyer +
                '}';
    }
}
