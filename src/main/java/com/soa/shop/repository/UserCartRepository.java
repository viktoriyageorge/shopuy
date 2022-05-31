package com.soa.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.shop.model.UserCart;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, String> {

    Optional<UserCart> findByUsername(String username);
}
