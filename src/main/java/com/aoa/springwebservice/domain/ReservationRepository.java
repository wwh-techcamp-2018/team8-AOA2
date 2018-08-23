package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAllByStore(Store store);
    //Reservation findTopByOpenDateBeforeOrderByOpenDateDesc(LocalDate localDate);
    //Reservation findFirstByOpenDateBeforeOrderByOpenDateDesc(LocalDate localDate);
    //List<Reservation> findAllByStoreIdAndOpenDate(long storeId, LocalDate openDate);
    List<Reservation> findAllByStoreId(long storeId);
}