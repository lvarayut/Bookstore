package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
* Bank account for users
*/
public class Account {
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("balance")
    private float balance;

    public Account(){}

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Account{" +
                "id='" + accountId + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
