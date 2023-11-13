package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.shop.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
