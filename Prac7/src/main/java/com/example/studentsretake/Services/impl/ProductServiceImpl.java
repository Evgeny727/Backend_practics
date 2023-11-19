package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Entities.Product;
import com.example.studentsretake.Repos.ProductRepo;
import com.example.studentsretake.Services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product read(int id) {
        return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No such product"));
    }

    @Override
    public Product update(int id, Product product) {
        Product prFromRepo = productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No such product"));

        prFromRepo.setName(product.getName());
        prFromRepo.setPrice(product.getPrice());
        prFromRepo.setQty(product.getQty());

        return productRepo.save(prFromRepo);
    }

    @Override
    public void delete(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> read() {
        return productRepo.findAll();
    }
}
