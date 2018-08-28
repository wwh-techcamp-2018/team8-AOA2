package com.aoa.springwebservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoreRepository extends CrudRepository<Store, Long>{
    Optional<Store> findByUserId(long id);
    Optional<Store> findByUser(User user);
}
