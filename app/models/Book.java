package models;


import com.fasterxml.jackson.annotation.*;
import org.jongo.*;
import uk.co.panaxiom.playjongo.*;

import java.util.Date;
import java.util.List;

public class Book {

    @JsonProperty("_id")
    private String id;
    private String name;
    private String author;
    private Date publicationDate;
    private String description;
    private String imagePath;
    @JsonCreator
    public Book(@JsonProperty("name")String name, @JsonProperty("author")String author, @JsonProperty("publicationDate")Date publicationDate, @JsonProperty("description")String description, @JsonProperty("imagePath")String imagePath) {
        this.name = name;
        this.author = author;
        this.publicationDate = publicationDate;
        this.description = description;
        this.imagePath = imagePath;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static MongoCollection books(){
        return PlayJongo.jongo().getCollection("books");
    }

    public void insert(){
        books().save(this);
    }

    public void updateName(String name){
        books().update("{name: #}",this.getName()).with("{name:#}",name);
    }

    public void remove(){
        books().remove(this.getId());
    }

    public static Iterable<Book> findAll(){
        Iterable<Book> allBooks = books().find().as(Book.class);
        return allBooks;
    }

    public static Book findByName(String name){
        return books().findOne("{name:#}",name).as(Book.class);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
