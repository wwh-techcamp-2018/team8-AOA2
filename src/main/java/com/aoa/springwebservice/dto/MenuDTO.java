package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.Store;

import java.io.Serializable;

public class MenuDTO implements Serializable {

    protected String name;
    protected int price;
    protected String description;
    protected String imageUrl;

    public MenuDTO() {
    }

    public MenuDTO(String name, int price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Menu toDomain() {
        return toDomain(null);
    }

    public Menu toDomain(Store store) {
        return toDomain(store, imageUrl);
    }

    public Menu toDomain(Store store, String imageUrl) {
        return new Menu(name, price, description, imageUrl, store);
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
