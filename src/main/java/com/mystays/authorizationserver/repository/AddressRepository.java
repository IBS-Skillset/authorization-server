package com.mystays.authorizationserver.repository;

import com.mystays.authorizationserver.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<UserAddress,Integer> {
}
