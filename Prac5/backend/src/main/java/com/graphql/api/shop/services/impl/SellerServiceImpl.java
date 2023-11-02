package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.users.Seller;
import com.graphql.api.shop.repositories.SellerRepository;
import com.graphql.api.shop.services.SellerService;
import com.graphql.api.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final UserService userService;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, UserService userService) {
        this.sellerRepository = sellerRepository;
        this.userService = userService;
    }

    @Override
    public Seller save(Seller seller) {
        seller.setUser(userService.save(seller.getUser()));
        return sellerRepository.save(seller);
    }

    @Override
    public Seller findById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public Seller updateById(Long aLong, Seller item) {
        return null;
    }

    @Override
    public List<Seller> saveAll(List<Seller> sellers) {
        return sellers.stream().map(this::save).toList();
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        sellerRepository.deleteById(id);
    }
}
