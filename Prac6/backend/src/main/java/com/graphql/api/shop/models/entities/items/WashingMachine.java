package com.graphql.api.shop.models.entities.items;

import com.graphql.api.shop.models.base.BaseEntity;
import com.graphql.api.shop.models.entities.Manufacturer;
import com.graphql.api.shop.models.entities.base.BaseItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class WashingMachine extends BaseEntity {
    @OneToOne
    BaseItem baseItem;

    BigInteger tankCapacity;

    @ManyToOne(fetch = FetchType.EAGER)
    Manufacturer manufacturer;
}
