package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findByStoreAndPickupTimeAfterOrderByPickupTime(Store store, LocalDateTime lastDay);
}
