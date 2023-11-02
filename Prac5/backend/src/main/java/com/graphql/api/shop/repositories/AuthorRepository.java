package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
