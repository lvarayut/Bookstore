package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.ArrayList;
import java.util.List;

/*
* Inventory is a class for managing
* book available for order
*/
public class Inventory {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private Address contact;
    @JsonProperty("products")
    private List<Product> products;

    public Inventory() {
        products = new ArrayList<Product>();
    }

    public static MongoCollection stocks() {
        return PlayJongo.getCollection("stocks");
    }

//    public static Iterable<Book> findAllInStock(String stockName) {
//        Inventory bookInventory = findByName(stockName);
//        return bookInventory.getBooks();
//    }

    public static Inventory findByName(String name) {
        return stocks().findOne("{name:#}", name).as(Inventory.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getContact() {
        return contact;
    }

    public void setContact(Address contact) {
        this.contact = contact;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void insert() {
        stocks().save(this);
    }

    public void updateBook(Inventory newInventory) {
        stocks().update("{name:#}", newInventory.getName()).with(newInventory);
    }

//    public void removeBook(Inventory inventory, String bookName) {
//        ArrayList<Book> books = inventory.getBooks();
//        for (int i = 0; i < books.size(); i++) {
//            if (books.get(i).getName().equals(bookName)) {
//                books.remove(i);
//            }
//        }
//        updateBook(inventory);
//    }


    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contact=" + contact +
                ", products=" + products +
                '}';
    }
}