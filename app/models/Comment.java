package models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Comment {
    @JsonProperty("user")
    private String user;
    @JsonProperty("publicationDate")
    private Date publicationDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("rating")
    private int rating;

    public Comment(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
