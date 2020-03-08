package com.bookstore.forreal.Model.Services;

import java.util.List;
import java.util.Optional;

public interface Services<T> {
    //T findByName(String name);
    List<T> findAll();
    List<T> findAllById(List<Integer> Ids);
    Optional<T> findById(Integer Id);
    void deleteById(Integer Id);
    void deleteAllById(List<Integer> Ids);
    void Save(T draft);
    void saveAll(List<T> drafts);
    boolean existById(int Id);
}
