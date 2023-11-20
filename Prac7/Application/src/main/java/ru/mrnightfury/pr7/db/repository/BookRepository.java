package ru.mrnightfury.pr7.db.repository;

import org.springframework.stereotype.Repository;
import ru.mrnightfury.pr7.db.model.Book;

@Repository
public interface BookRepository extends ProductRepository<Book> {
}