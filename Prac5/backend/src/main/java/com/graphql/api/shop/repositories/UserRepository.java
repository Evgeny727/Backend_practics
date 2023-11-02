package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
