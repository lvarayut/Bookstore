package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Order {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("productIds")
    private List<String> productIds;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("total")
    private float total;
    @JsonProperty("userId")
    private String userId;


    public Order() {
        productIds = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", products=" + productIds +
                ", quantity=" + quantity +
                ", total=" + total +
                ", userId=" + userId +
                '}';
    }
}
