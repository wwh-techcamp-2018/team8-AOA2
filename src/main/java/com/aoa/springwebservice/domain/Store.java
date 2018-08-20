package com.aoa.springwebservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @NoArgsConstructor
@Entity
@ToString
public class Store{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //todo length 등 다른 조건들
    @Column(nullable = false, length = 40)
    private String storeName;

    @Column(nullable = false, length = 60)
    private String serviceDescription;

    @Column(nullable = false, length = 10)
    private String ownerName;

    @Column(nullable = true, length = 300)
    private String imgURL;

    @Column(nullable = false, length = 5)
    private String postCode;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = true, length = 40)
    private String addressDetail;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = true, length = 600)
    private String description;

    // Todo Cascade issue 다른 옵션도 적용해야 할 수도 있음
    @OneToMany(mappedBy = "store", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Store(String storeName, String serviceDescription, String ownerName, String imgURL, String postCode, String address, String addressDetail, String phoneNumber, String description) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.imgURL = imgURL;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public void addMenu(Menu menu) {
        if(menu != null && !hasMenu(menu)) {
            menu.setStore(this);
            menus.add(menu);
        }
    }

    public boolean hasMenu(Menu menu) {
        return menus.contains(menu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //todo User 추가되면 Store의 unique 제약조건 다시 생각
        Store store = (Store) o;
        return Objects.equals(storeName, store.storeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(storeName);
    }
}
