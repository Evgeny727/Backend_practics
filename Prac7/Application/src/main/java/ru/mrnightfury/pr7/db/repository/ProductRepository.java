package ru.mrnightfury.pr7.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrnightfury.pr7.db.model.Product;

@Repository
public interface ProductRepository<T extends Product> extends JpaRepository<T, Long> {
}
