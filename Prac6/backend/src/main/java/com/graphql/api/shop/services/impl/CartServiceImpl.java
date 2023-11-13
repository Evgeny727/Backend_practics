package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.shop.Cart;
import com.graphql.api.shop.models.shop.CountItem;
import com.graphql.api.shop.repositories.CartRepository;
import com.graphql.api.shop.services.CartService;
import com.graphql.api.shop.services.CountItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    final CartRepository cartRepository;
    final CountItemService countItemService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CountItemService countItemService) {
        this.cartRepository = cartRepository;
        this.countItemService = countItemService;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart updateById(Long aLong, Cart item) {
        return null;
    }

    @Override
    public List<Cart> saveAll(List<Cart> items) {
        return null;
    }

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public CountItem addItem(Long cartId, CountItem item) {
        Cart cart = findById(cartId);
        item = countItemService.save(item);
        cart.getItems().add(item);
        save(cart);
        return item;
    }

    @Override
    public void deleteItemById(Long cartId, Long idCountItem) {
        Cart cart = findById(cartId);
        cart.getItems().removeIf(countItem -> Objects.equals(countItem.getId(), idCountItem));
        cartRepository.save(cart);
    }


    @Override
    public List<CountItem> getAllItems(Long cartId) {
        return findById(cartId).getItems();
    }

    @Override
    public Cart setOrder(Long cartId) {
        Cart cart = findById(cartId);
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (CountItem item: cart.getItems()) {
            Long count = item.getCount();

            if (count > item.getItem().getCount()) {
                throw new RuntimeException("The product is over");
            }

            BigDecimal countBigDecimal = BigDecimal.valueOf(count);
            BigDecimal itemPrice = item.getItem().getPrice();
            totalMoney = totalMoney.add(countBigDecimal.multiply(itemPrice));
        }
        cart.setItems(new ArrayList<>());
        return save(cart);
    }

    @Override
    public CountItem editCountByCartIdAndItemId(Long cartId, Long countItemId, Long count) {
        Cart cart = findById(cartId);
        CountItem item = null;
        for (CountItem countItem : cart.getItems()) {
            if (Objects.equals(countItem.getId(), countItemId)) {
                countItem.setCount(count);
                item = countItemService.save(countItem);
            }
        }
        return item;
    }
}
