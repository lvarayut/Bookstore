package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
* Bank account for users
*/
public class Account {
    @JsonProperty("_id")
    private String id;
    private float balance;

    @JsonCreator
    public Account(@JsonProperty("balance") int balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void deposit(float money) {
        this.setBalance(this.getBalance() + money);
    }

    public void withdraw(float money) {
        this.setBalance(this.getBalance() - money);
    }
}
