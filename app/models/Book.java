package models;


import com.fasterxml.jackson.annotation.*;
import org.jongo.*;
import uk.co.panaxiom.playjongo.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
* Book is a product of our store
*/
public class Book extends Product{

    @JsonProperty("category")
    private String category;
    @JsonProperty("author")
    private String author;
    @JsonProperty("publicationDate")
    private Date publicationDate;
    @JsonProperty("numPage")
    private int numPage;

    public Book() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    @Override
    public String toString() {
        return "Book{" +
                "category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", numPage=" + numPage +
                "} " + super.toString();
    }
}
