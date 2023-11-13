package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.items.Telephone;
import com.graphql.api.shop.models.entities.base.BaseItem;
import com.graphql.api.shop.repositories.TelephoneRepository;
import com.graphql.api.shop.services.ItemService;
import com.graphql.api.shop.services.TelephoneService;
import com.graphql.api.shop.services.utils.UpdateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {
    private final TelephoneRepository telephoneRepository;
    private final ItemService itemService;

    @Autowired
    public TelephoneServiceImpl(TelephoneRepository telephoneRepository, ItemService itemService) {
        this.telephoneRepository = telephoneRepository;
        this.itemService = itemService;
    }

    @Override
    public Telephone save(Telephone telephone) {
        telephone.setBaseItem(itemService.save(telephone.getBaseItem()));
        telephone = telephoneRepository.saveAndFlush(telephone);
        telephoneRepository.clear();
        return findById(telephone.getId());
    }

    @Override
    public Telephone findById(Long id) {
        return telephoneRepository.findById(id).orElse(null);
    }

    @Override
    public List<Telephone> saveAll(List<Telephone> telephones) {
        return telephones.stream().map(this::save).toList();
    }

    @Override
    public Telephone updateById(Long id, Telephone telephone) {
        Telephone lastTelephone = findById(id);
        if (lastTelephone == null) {
            return null;
        }

        if (telephone.getBaseItem() != null) {
            telephone.setBaseItem(itemService.updateById(
                    lastTelephone.getBaseItem().getId(),
                    telephone.getBaseItem()
            ));
        }

        UpdateFields.updateField(telephone, lastTelephone);
        return save(lastTelephone);
    }

    @Override
    public List<Telephone> findAll() {
        return telephoneRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        BaseItem item = findById(id).getBaseItem();
        telephoneRepository.deleteById(id);
        itemService.deleteById(item.getId());
    }
}
