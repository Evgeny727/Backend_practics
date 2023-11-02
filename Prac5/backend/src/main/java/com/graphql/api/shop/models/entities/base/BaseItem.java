package com.graphql.api.shop.models.entities.base;

import com.graphql.api.shop.models.base.BaseEntity;
import com.graphql.api.shop.models.entities.Category;
import com.graphql.api.shop.models.users.Seller;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseItem extends BaseEntity {
    String name;
    String description;
    BigDecimal price;

    Long count;

    @ManyToMany
    List<Category> categories;

    @ManyToOne
    Seller seller;
}
