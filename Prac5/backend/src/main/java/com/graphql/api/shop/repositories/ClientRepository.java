package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
