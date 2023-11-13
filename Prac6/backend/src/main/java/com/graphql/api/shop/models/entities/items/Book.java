package com.graphql.api.shop.models.entities.items;

import com.graphql.api.shop.models.base.BaseEntity;
import com.graphql.api.shop.models.entities.base.BaseItem;
import com.graphql.api.shop.models.entities.Author;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
public class Book extends BaseEntity {
    @OneToOne
    BaseItem baseItem;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Author> authors;
}
