package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.users.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
