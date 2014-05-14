package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Product {
    @JsonProperty("_id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("description")
    protected String description;
    @JsonProperty("company")
    protected String company;
    @JsonProperty("price")
    protected float price;
    @JsonProperty("image")
    protected byte[] image;

    public Product() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return  "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                ", image=" + Arrays.toString(image);
    }
}
