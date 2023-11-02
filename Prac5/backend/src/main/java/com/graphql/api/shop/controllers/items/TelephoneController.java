package com.graphql.api.shop.controllers.items;

import com.graphql.api.shop.models.entities.items.Telephone;
import com.graphql.api.shop.services.TelephoneService;
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
public class TelephoneController {
    private final TelephoneService telephoneService;

    @Autowired
    public TelephoneController(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    @MutationMapping
    public Telephone createTelephone(@Argument Telephone telephone) {
        return telephoneService.save(telephone);
    }

    @MutationMapping
    public Telephone updateTelephoneById(@Argument Long id, @Argument Telephone telephone) {
        return telephoneService.updateById(id, telephone);
    }

    @MutationMapping
    public void deleteTelephoneById(@Argument Long id) {
        telephoneService.deleteById(id);
    }

    @QueryMapping
    public Telephone findTelephoneById(@Argument Long id) {
        return telephoneService.findById(id);
    }

    @QueryMapping
    public List<Telephone> findAllTelephones() {
        return telephoneService.findAll();
    }
}
