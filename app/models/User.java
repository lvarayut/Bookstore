package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;


public class User {

    @JsonProperty("_id")
    private String id;

    private String name;

    public User(String name) {
        this.name = name;
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

    // Get users collection
    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }

    public void  insert() {
        users().save(this);
    }

    public void updateName(String name){
        users().update("{name: #}",this.getName()).with("{name:#}",name);
    }

    public void remove() {
        users().remove(this.getId());
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}