package com.graphql.api.shop.models.shop;

import com.graphql.api.shop.models.base.BaseEntity;
import com.graphql.api.shop.models.entities.base.BaseItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity {
    @OneToMany(fetch = FetchType.EAGER)
    List<CountItem> items;
}
