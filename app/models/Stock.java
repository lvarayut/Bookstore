package models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.ArrayList;

/*
* Stock is a class for managing
* book available for order
*/
public class Stock {
    @JsonProperty("_id")
    private String id;

    private String name;

    private ArrayList<Book> books;

    @JsonCreator
    public Stock(@JsonProperty("name") String name, @JsonProperty("books") ArrayList<Book> books) {
        this.name = name;
        this.books = books;
    }

    public static MongoCollection stocks() {
        return PlayJongo.getCollection("stocks");
    }

    public static Iterable<Book> findAllInStock(String stockName) {
        Stock bookStock = findByName(stockName);
        return bookStock.getBooks();
    }

    public static Stock findByName(String name) {
        return stocks().findOne("{name:#}", name).as(Stock.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
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

    public void updateBook(Stock newStock) {
        stocks().update("{name:#}", newStock.getName()).with(newStock);
    }

    public void removeBook(Stock stock, String bookName) {
        ArrayList<Book> books = stock.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equals(bookName)) {
                books.remove(i);
            }
        }
        updateBook(stock);
    }
}