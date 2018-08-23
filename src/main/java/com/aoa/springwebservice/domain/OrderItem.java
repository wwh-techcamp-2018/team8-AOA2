package com.aoa.springwebservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class OrderItem {

    private Order order;
    private Menu menu;
    private int itemCount;
    private int itemTotalPrice;

}

/*
    DTO -- >
    - 가격은 안받고 메뉴, 갯수만 알면
   1. Order with  List< OrderItem>
   2. Order - User, Store Mapping
        2.1 User - Customer 타입? / User -- 대신 다른 형식으로 저장?
        2.2 현재는 비회원 (로그인) --> Cascade / User Save 따로
   3. OrderItem - Order, Menu Mapping
    ( 2, 3 - Cascade / Order - OrderItem bidrectional ?)
   4.Order -- 총 비용, 수령시간, status 세팅된다

Order <---> OrderItem

Order ---> User
Order ---> Store
OrderItem --> Menu

*/