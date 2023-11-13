package com.graphql.api.shop.controllers.shop;

import com.graphql.api.shop.models.shop.Cart;
import com.graphql.api.shop.models.shop.CountItem;
import com.graphql.api.shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigInteger;
import java.util.List;

@Component
@Controller
@CrossOrigin
public class CartController {
    final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @MutationMapping
    CountItem addCountItem(@Argument Long cartId, @Argument CountItem item) {
        return cartService.addItem(cartId, item);
    }

    @MutationMapping
    void deleteCountItemById(@Argument Long cartId, @Argument Long countItemId) {
        cartService.deleteItemById(cartId, countItemId);
    }

    @MutationMapping
    CountItem editCountByCartIdAndItemId(
            @Argument Long cartId,
            @Argument Long countItemId,
            @Argument Long count) {
        return cartService.editCountByCartIdAndItemId(cartId, countItemId, count);
    }

    @MutationMapping
    Cart setOrder(@Argument Long cartId) {
        return cartService.setOrder(cartId);
    }

    @QueryMapping
    List<CountItem> findAllItemInCartById(@Argument Long cartId) {
        return cartService.getAllItems(cartId);
    }
}
