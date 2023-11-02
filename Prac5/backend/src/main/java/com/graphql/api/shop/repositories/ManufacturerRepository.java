package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
