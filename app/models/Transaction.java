package models;


import com.fasterxml.jackson.annotation.*;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class Transaction {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("orderProduct")
    private Order orderProduct;
    private String status;

    public Transaction() {
    }

    // Get users collection
    public static MongoCollection transactions() {
        return PlayJongo.getCollection("transactions");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(Order orderProduct) {
        this.orderProduct = orderProduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void insert() {
        transactions().save(this);
    }

    public void updateStatus(String status) {
        transactions().update("{_id:#}", this.getId()).with("{$set: {status:#}}", status);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", orderProduct=" + orderProduct +
                ", status='" + status + '\'' +
                '}';
    }

    public void printOut() {
        Transaction transaction = transactions().findOne("{_id:#}", this.getId()).as(Transaction.class);
        System.out.println(transaction.toString());

    }
}
