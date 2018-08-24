package com.aoa.springwebservice.service.support;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.Store;

import java.util.List;

public interface ReservationSelector {
    List<Reservation> select(Store store);
}