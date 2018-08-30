package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByStoreAndPickupTimeAfterOrderByPickupTime(Store store, LocalDateTime lastDay);
}
