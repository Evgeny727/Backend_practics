package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.Manufacturer;
import com.graphql.api.shop.repositories.ManufacturerRepository;
import com.graphql.api.shop.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public Manufacturer updateById(Long id, Manufacturer item) {
        return null;
    }

    @Override
    public List<Manufacturer> saveAll(List<Manufacturer> manufacturers) {
        return manufacturerRepository.saveAll(manufacturers);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
