package com.graphql.api.shop.models.shop;

import com.graphql.api.shop.models.base.BaseEntity;
import com.graphql.api.shop.models.entities.base.BaseItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    BaseItem item;
    Long count;
}
