package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.items.WashingMachine;
import com.graphql.api.shop.models.entities.base.BaseItem;
import com.graphql.api.shop.repositories.WashingMachineRepository;
import com.graphql.api.shop.services.ItemService;
import com.graphql.api.shop.services.WashingMachineService;
import com.graphql.api.shop.services.utils.UpdateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WashingMachineServiceImpl implements WashingMachineService {
    private final WashingMachineRepository washingMachineRepository;
    private final ItemService itemService;

    @Autowired
    public WashingMachineServiceImpl(WashingMachineRepository washingMachineRepository, ItemService itemService) {
        this.washingMachineRepository = washingMachineRepository;
        this.itemService = itemService;
    }

    @Override
    public WashingMachine save(WashingMachine washingMachine) {
        washingMachine.setBaseItem(itemService.save(washingMachine.getBaseItem()));
        washingMachine = washingMachineRepository.saveAndFlush(washingMachine);
        washingMachineRepository.clear();
        return findById(washingMachine.getId());
    }

    @Override
    public WashingMachine findById(Long id) {
        return washingMachineRepository.findById(id).orElse(null);
    }

    @Override
    public List<WashingMachine> saveAll(List<WashingMachine> washingMachines) {
        return washingMachines.stream().map(this::save).toList();
    }

    @Override
    public WashingMachine updateById(Long id, WashingMachine washingMachine) {
        WashingMachine lastWashingMachine = findById(id);
        if (lastWashingMachine == null) {
            return null;
        }
        if (washingMachine.getBaseItem() != null) {
            washingMachine.setBaseItem(itemService.updateById(
                    lastWashingMachine.getBaseItem().getId(),
                    washingMachine.getBaseItem()
            ));
        }
        UpdateFields.updateField(washingMachine, lastWashingMachine);
        return save(lastWashingMachine);
    }


    @Override
    public List<WashingMachine> findAll() {
        return washingMachineRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        BaseItem item = findById(id).getBaseItem();
        washingMachineRepository.deleteById(id);
        itemService.deleteById(item.getId());
    }
}
