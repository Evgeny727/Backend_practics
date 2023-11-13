package com.graphql.api.shop.controllers;

import com.graphql.api.shop.models.entities.Manufacturer;
import com.graphql.api.shop.services.ManufacturerService;
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
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @MutationMapping
    public Manufacturer createManufacturer(@Argument Manufacturer manufacturer) {
        return manufacturerService.save(manufacturer);
    }

    @MutationMapping
    public void deleteManufacturerById(@Argument Long id) {
        manufacturerService.deleteById(id);
    }

    @QueryMapping
    public Manufacturer findManufacturerById(@Argument Long id) {
        return manufacturerService.findById(id);
    }

    @QueryMapping
    public List<Manufacturer> findAllManufacturers() {
        return manufacturerService.findAll();
    }
}
