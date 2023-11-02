package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.shop.Cart;
import com.graphql.api.shop.repositories.CartRepository;
import com.graphql.api.shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

}
