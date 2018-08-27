package com.aoa.springwebservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(nullable = false)),
            @AttributeOverride(name = "phoneNumber", column = @Column(nullable = false))
    })
    @JsonUnwrapped
    private Customer customer;

    @ManyToOne
    private Store store;

    private LocalDateTime createdDate;

    private LocalDateTime pickupTime;

    private int orderTotalPrice;

//    @JsonProperty
//    @JsonSerialize(using=NumericBooleanSerializer.class)
//    @JsonDeserialize(using=NumericBooleanDeserializer.class)
    private Boolean isPickedup;

    //Todo :: OrderBy
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    //Todo :: 다른 상태 있는지 고려해야함. 미수령/수령이 아니라 취소나 수령대기나 등등등

    @Builder
    public Order(Customer customer, Store store, LocalDateTime pickupTime) {
        this.customer = customer;
        this.store = store;
        this.createdDate = LocalDateTime.now();
        this.pickupTime = pickupTime;
        this.orderTotalPrice = 0;
        this.isPickedup = false;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.orderTotalPrice += orderItem.getItemTotalPrice();
    }

    public void setIsPickedupByOrder(Order inputOrder) {
        if(inputOrder.isPickedup)
            this.isPickedup = false;
        else
            this.isPickedup = true;
    }

//    public static class NumericBooleanSerializer extends JsonSerializer<Boolean> {
//
//        @Override
//        public void serialize(Boolean bool, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
//            generator.writeString(bool ? "1" : "0");
//        }
//    }
//
//    public static class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {
//
//        @Override
//        public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
//            return !"0".equals(parser.getText());
//        }
//    }
}
