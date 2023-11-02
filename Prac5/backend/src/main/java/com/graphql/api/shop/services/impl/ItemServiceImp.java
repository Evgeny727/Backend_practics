package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.entities.base.BaseItem;
import com.graphql.api.shop.repositories.ItemRepository;
import com.graphql.api.shop.services.ItemService;
import com.graphql.api.shop.services.utils.UpdateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ItemServiceImp implements ItemService {
    final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImp(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public BaseItem save(BaseItem item) {
        return itemRepository.save(item);
    }

    @Override
    public BaseItem findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public BaseItem updateById(Long id, BaseItem item) {
        BaseItem lastItem = findById(id);
        if (lastItem == null) {
            return null;
        }

        UpdateFields.updateField(item, lastItem);
        return save(lastItem);
    }

    @Override
    public List<BaseItem> saveAll(List<BaseItem> items) {
        return itemRepository.saveAll(items);
    }

    @Override
    public List<BaseItem> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
