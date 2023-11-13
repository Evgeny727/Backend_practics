package com.graphql.api.shop.controllers;

import com.graphql.api.shop.models.users.Seller;
import com.graphql.api.shop.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
@Controller
@CrossOrigin
public class SellerController {
    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @MutationMapping
    public Seller createSeller(@Argument Seller seller) {
        return sellerService.save(seller);
    }

    @MutationMapping
    public void deleteSellerById(@Argument Long id) {
        sellerService.deleteById(id);
    }

    @QueryMapping
    public Seller findSellerById(@Argument Long id) {
        return sellerService.findById(id);
    }

    @QueryMapping
    public List<Seller> findAllSellers() {
        return sellerService.findAll();
    }
}
