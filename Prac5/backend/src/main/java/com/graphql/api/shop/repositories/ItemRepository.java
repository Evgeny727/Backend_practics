package com.graphql.api.shop.repositories;

import com.graphql.api.shop.models.entities.base.BaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<BaseItem, Long> {
}
