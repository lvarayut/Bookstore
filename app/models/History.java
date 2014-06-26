package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("products")
    private List<Product> products; // Sellers are included
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("total")
    private float total;
    @JsonProperty("buyer")
    private User buyer;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("paymentMethod")
    private String paymentMethod;

    public History() {
        products = new ArrayList<Product>();
        date = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", quantity=" + quantity +
                ", total=" + total +
                ", buyer=" + buyer +
                ", date=" + date +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
