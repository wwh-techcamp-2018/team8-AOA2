package com.aoa.springwebservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Menu {

    private static final boolean LAST_USED = true;
    private static final boolean NOT_LAST_USED = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String description;

    @Column(nullable = false, length = 400)
    private String imageUrl;

    private boolean deleted;

    @Embedded
    private MaxCount maxCount;

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_menu_store"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Store store;

    private boolean lastUsed; //= false;

    public Menu() {

    }

    public Menu(String name, int price, String description, String imageUrl) {
        this(name, price, description, imageUrl, null);
    }

    public Menu(String name, int price, String description, String imageUrl, Store store) {
        this(0, name, price, description, imageUrl, store);
    }

    @Builder
    public Menu(long id, String name, int price, String description, String imageUrl, Store store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.store = store;
        this.deleted = false;
        this.maxCount = new MaxCount(2,1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return price == menu.price &&
                Objects.equals(name, menu.name) &&
                Objects.equals(description, menu.description) &&
                Objects.equals(imageUrl, menu.imageUrl) &&
                Objects.equals(store, menu.store);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, store);
    }

    public boolean isEqualStore(Store store) {
        return this.store.equals(store);
    }


    public void deleteMenu() {
        this.deleted = true;
        return;
    }

    public void setUpLastUsedStatus(MaxCount maxCount) {
        this.maxCount = maxCount;
        this.lastUsed = LAST_USED;
    }

    public void dropLastUsedStatus() {
        lastUsed = NOT_LAST_USED;
    }

    public boolean isLastUsed() {
        return lastUsed;
    }

    public int calculatePrice(int itemCount) {
        return this.price * itemCount;
    }

    public boolean hasSameStore(Store store) {
        return this.store.equals(store);
    }
}
