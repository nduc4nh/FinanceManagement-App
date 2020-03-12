package com.bookstore.forreal.Model.Services;
import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Repository.AuthorRepository;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    
    
    @Override
    public Author findByName(String name) {
        // TODO Auto-generated method stub
        List<Author> authors = (List<Author>) repository.findAll();
        for (Author ele:authors)
        {
            if (preprocessString.doString(ele.getName()).equals(preprocessString.doString(name))) return ele;
        }
        return null;
    }
    
    @Override
    public List<Author> findAllByName(String[] names) {
        // TODO Auto-generated method stub
        ArrayList <Author> re = new ArrayList<Author>();
        for (String name:names)
        {
            re.add(findByName(name)); 
        }
        return re;
    }
  
    @Override
    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }

    @Override
    public List<Author> findAllById(List<Integer> Ids) {
        return (List<Author>) repository.findAllById(Ids);
    }
   
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
