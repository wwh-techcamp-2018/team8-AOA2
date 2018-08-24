package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAllByStore(Store store);
    List<Reservation> findAllByStoreId(long storeId);
    List<Reservation> findAllByStoreAndOpenDate(Store store, LocalDate openDate);
    Optional<Reservation> findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(Store defaultStore, LocalDate now);
}
