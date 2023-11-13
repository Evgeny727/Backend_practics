package com.graphql.api.shop.services;

import com.graphql.api.shop.models.shop.Cart;
import com.graphql.api.shop.models.shop.CountItem;
import com.graphql.api.shop.services.base.BaseCRUDService;

import java.math.BigInteger;
import java.util.List;

public interface CartService extends BaseCRUDService<Cart, Long> {
    CountItem addItem(Long cartId, CountItem item);

    void deleteItemById(Long cartId, Long id);

    List<CountItem> getAllItems(Long cartId);

    Cart setOrder(Long cartId);

    CountItem editCountByCartIdAndItemId(Long cartId, Long countItemId, Long count);
}
