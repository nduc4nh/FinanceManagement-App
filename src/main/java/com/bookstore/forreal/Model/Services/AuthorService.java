package com.bookstore.forreal.Model.Services;
import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements Services<Author> {
    @Autowired
    AuthorRepository repository;
    /*
    @Override
    public Author findByName(String name) {
        return null;
    }*/
    @Transactional
    @Override
    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }
    @Transactional
    @Override
    public List<Author> findAllById(List<Integer> Ids) {
        return (List<Author>) repository.findAllById(Ids);
    }
    @Transactional
    @Override
    public Optional<Author> findById(Integer Id) {
        return repository.findById(Id);
    }
    @Transactional
    @Override
    public void deleteById(Integer Id) {
        repository.deleteById(Id);
    }
    @Transactional
    @Override
    public void deleteAllById(List<Integer> Ids) {
        //repository.deleteAll(Ids);
    }
    @Transactional
    @Override
    public void Save(Author draft) {
        repository.save(draft);
    }
    @Transactional
    @Override
    public void saveAll(List<Author> drafts) {
        repository.saveAll(drafts);
    }

    @Override
    public boolean existById(int Id) {
        return repository.existsById(Id);
    }

}
