package com.aoa.springwebservice.test;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPATestDomainRepository extends CrudRepository<JPATestDomain, Long> {
}
