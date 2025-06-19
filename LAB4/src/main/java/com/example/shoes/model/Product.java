package com.example.shoes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shoes")
public class Product {

    @Id
    private String id;
    private String name;
    private int price;
    private String brand;
    private String description;

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
}

