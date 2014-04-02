package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

/*
* User is an account for persons
* in the application
*/

public class User {

    @JsonProperty("_id")
    private String id;

    private String name;

    private Account account;

    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("account") Account account) {
        this.name = name;
        this.account = account;
    }

    // Get users collection
    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void insert() {
        users().save(this);
    }

    public void updateName(String name) {
        users().update("{name: #}", this.getName()).with("{name:#}", name);
    }

    public void remove() {
        users().remove(this.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}