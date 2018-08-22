package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAllByStore(Store store);
}
