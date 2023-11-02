package com.graphql.api.shop.controllers.items;

import com.graphql.api.shop.models.entities.items.WashingMachine;
import com.graphql.api.shop.services.WashingMachineService;
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
public class WashingMachineController {
    private final WashingMachineService washingMachineService;

    @Autowired
    public WashingMachineController(WashingMachineService washingMachineService) {
        this.washingMachineService = washingMachineService;
    }

    @MutationMapping
    public WashingMachine createWashingMachine(@Argument WashingMachine washingMachine) {
        return washingMachineService.save(washingMachine);
    }

    @MutationMapping
    public WashingMachine updateWashingMachineById(@Argument Long id, @Argument WashingMachine washingMachine) {
        return washingMachineService.updateById(id, washingMachine);
    }

    @MutationMapping
    public void deleteWashingMachineById(@Argument Long id) {
        washingMachineService.deleteById(id);
    }

    @QueryMapping
    public WashingMachine findWashingMachineById(@Argument Long id) {
        return washingMachineService.findById(id);
    }

    @QueryMapping
    public List<WashingMachine> findAllWashingMachines() {
        return washingMachineService.findAll();
    }
}
