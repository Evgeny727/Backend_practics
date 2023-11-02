package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.entities.items.Book;
import com.graphql.api.shop.repositories.repository.CustomRepository;

public interface BookRepository extends CustomRepository<Book, Long> {
}
