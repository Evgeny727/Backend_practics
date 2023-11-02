package com.graphql.api.shop.services.base;


import java.io.Serializable;
import java.util.List;

public interface BaseCRUDService <T, ID extends Serializable>{
    T save(T item);
    T findById(ID id);
    T updateById(ID id, T item);
    List<T> saveAll(List<T> items);
    List<T> findAll();
    void deleteById (ID id);
}
