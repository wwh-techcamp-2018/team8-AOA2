package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.MenuOutputDTO;
import com.aoa.springwebservice.exception.InvalidStateOnStore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter @NoArgsConstructor
@Entity
@ToString @Slf4j
public class Store{

    private static final boolean OPEN = true;
    private static final boolean CLOSE = false;

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

    @Column(nullable = false, length = 400)
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

    @OneToOne
    private User user;

    // Todo 제약사항 추가
    private LocalDateTime timeToClose;

    // Todo Cascade issue 다른 옵션도 적용해야 할 수도 있음
    @OneToMany(mappedBy = "store", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Where(clause = "deleted = false")
    private List<Menu> menus = new ArrayList<>();

    // todo (현재 시각이랑 timeToClose 랑 비교) +(currentReservations 갯수?)해서 오픈상태 동기화 어떻게 해줄지
    @Transient
    private boolean isOpen = false;

    @Builder
    public Store(String storeName, String serviceDescription, String ownerName, String imgURL, String postCode, String address, String addressDetail, String phoneNumber, String description, LocalDateTime timeToClose, boolean isOpen, User user) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.imgURL = imgURL;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.timeToClose = timeToClose;
        this.isOpen = isOpen;
        this.user = user;
    }


    public boolean addMenu(Menu menu) {
        if(menu != null && menu.isEqualStore(this) && !hasMenu(menu)) {
            menus.add(menu);
            return true;
        }
        return false;
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
        return Objects.equals(storeName, store.storeName) &&
                Objects.equals(user, store.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName, user);
    }

    public boolean isOpen(){
        updateOpenStatus();
        return isOpen;
    }

    @PostPersist @PostUpdate @PostLoad
    public void updateOpenStatus(){
        if(timeToClose == null || timeToClose.isBefore(LocalDateTime.now())) {
            isOpen = CLOSE;
            return;
        }
        isOpen = OPEN;
    }

    public void deactivate() {
        timeToClose = null;
        isOpen = CLOSE;
    }

    public void activate(LocalDateTime timeToClose){
        menus.stream().forEach(menu -> menu.dropLastUsedStatus());
        this.timeToClose = timeToClose;
        isOpen = OPEN;
    }

    public List<MenuOutputDTO> getMenuOutputDTOList() {
        List<MenuOutputDTO> menuDTOs = new ArrayList<>();
        this.menus.stream().forEach(e -> menuDTOs.add(MenuOutputDTO.createUsedMenuOutputDTO(e)));
        return menuDTOs;
    }

    public List<MenuOutputDTO> getUsedMenuOutputDTOList(){
        List<MenuOutputDTO> menuDTOs = new ArrayList<>();
        this.menus.stream().filter(Menu::isLastUsed).forEach(e -> menuDTOs.add(MenuOutputDTO.createUsedMenuOutputDTO(e)));
        return menuDTOs;
    }

    public List<Menu> getActiveMenus() {
        return this.menus.stream().filter(Menu::isLastUsed).collect(Collectors.toList());
    }

    public void updateLastUsedMenu(Menu menu, MaxCount maxCount) {
        if(!this.isOpen)
            throw new InvalidStateOnStore("Cannot update menu status on closed store");
        this.menus.stream().filter(x -> x.equals(menu)).findAny().orElseThrow( () -> new InvalidStateOnStore("Cannot find menu on store"))
                .setUpLastUsedStatus(maxCount);
    }

    public boolean hasSameOwner(User other) {
        return this.user.equals(other);
    }

    public Store updateStore(Store store) {
        this.serviceDescription = store.serviceDescription;
        this.postCode = store.postCode;
        this.address = store.address;
        this.addressDetail = store.addressDetail;
        this.phoneNumber = store.phoneNumber;
        this.description = store.description;
        this.imgURL = store.imgURL;
        return this;
    }
}
