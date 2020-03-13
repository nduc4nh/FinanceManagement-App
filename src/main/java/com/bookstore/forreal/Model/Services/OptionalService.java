package com.bookstore.forreal.Model.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookstore.forreal.Model.Entities.OptionalEntity;
import com.bookstore.forreal.Model.Repository.OptionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionalService implements Services<OptionalEntity> {
    @Autowired
    OptionalRepository repository;
    @Override
    public OptionalEntity findByName(String name) {
        // TODO Auto-generated method stub
        List<OptionalEntity> langs = (List<OptionalEntity>) repository.findAll();
        for (OptionalEntity ele:langs)
        {
            if (ele.getName().strip().toLowerCase().equals(name.strip().toLowerCase())) return ele;
        }
        return null;
    }
    
    @Override
    public List<OptionalEntity> findAllByName(String[] names) {
        // TODO Auto-generated method stub
        ArrayList <OptionalEntity> re = new ArrayList<OptionalEntity>();
        for (String name:names)
        {
            re.add(findByName(name)); 
        }
        return re;
    }

    @Override
    public List<OptionalEntity> findAll() {
        // TODO Auto-generated method stub
        return (List<OptionalEntity>) repository.findAll();
    }

    @Override
    public List<OptionalEntity> findAllById(List<Integer> Ids) {
        // TODO Auto-generated method stub
        return (List<OptionalEntity>) repository.findAllById(Ids);
    }

    @Override
    public void deleteById(Integer Id) {
        // TODO Auto-generated method stub
        repository.deleteById(Id);
    }

    @Override
    public void deleteAllById(List<Integer> Ids) {
        // TODO Auto-generated method stub
    }

    @Override
    public void Save(OptionalEntity draft) {
        // TODO Auto-generated method stub
        repository.save(draft);
    }

    @Override
    public void saveAll(List<OptionalEntity> drafts) {
        // TODO Auto-generated method stub
        repository.saveAll(drafts);
    }

    @Override
    public boolean existById(int Id) {
        // TODO Auto-generated method stub
        return repository.existsById(Id);
    }

    @Override
    public Optional<OptionalEntity> findById(Integer Id) {
        // TODO Auto-generated method stub
        return repository.findById(Id);
    }

}