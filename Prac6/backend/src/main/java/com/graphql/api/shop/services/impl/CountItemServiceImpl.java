package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.shop.CountItem;
import com.graphql.api.shop.repositories.CountItemRepository;
import com.graphql.api.shop.services.CountItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountItemServiceImpl implements CountItemService {
    final CountItemRepository countItemRepository;

    @Autowired
    public CountItemServiceImpl(CountItemRepository countItemRepository) {
        this.countItemRepository = countItemRepository;
    }

    @Override
    public CountItem save(CountItem item) {
        item = countItemRepository.saveAndFlush(item);
        countItemRepository.clear();
        item = findById(item.getId());
        if (item.getCount() > item.getItem().getCount()) {
            throw new RuntimeException("The product is over");
        }
        return item;
    }

    @Override
    public CountItem findById(Long id) {
        return countItemRepository.findById(id).orElse(null);
    }

    @Override
    public CountItem updateById(Long id, CountItem item) {
        return null;
    }

    @Override
    public List<CountItem> saveAll(List<CountItem> items) {
        return countItemRepository.saveAll(items);
    }

    @Override
    public List<CountItem> findAll() {
        return countItemRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        countItemRepository.deleteById(id);
    }
}
