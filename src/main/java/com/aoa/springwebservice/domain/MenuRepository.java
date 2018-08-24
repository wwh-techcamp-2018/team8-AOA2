package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
    List<Menu> findAllByStore(long storeId);
}
