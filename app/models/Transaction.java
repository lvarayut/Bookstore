package models;


import com.fasterxml.jackson.annotation.*;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class Transaction {
    @JsonProperty("_id")
    private String id;
    private String source;
    private String destination;
    private float value;
    private String status;

    @JsonCreator
    public Transaction(@JsonProperty("source") String source, @JsonProperty("destination") String destination, @JsonProperty("value") float value, @JsonProperty("status") String status) {
        this.source = source;
        this.destination = destination;
        this.value = value;
        this.status = status;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", value=" + value +
                ", status='" + status + '\'' +
                '}';
    }

    public void printOut() {
        Transaction transaction = transactions().findOne("{_id:#}", this.getId()).as(Transaction.class);
        System.out.println(transaction.toString());

    }
}
