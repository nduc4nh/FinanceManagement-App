package com.bookstore.forreal.Model.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookstore.forreal.Model.Entities.Image;
import com.bookstore.forreal.Model.Repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements Services<Image> {
    @Autowired
    ImageRepository repository;
    @Override
    public Image findByName(String name) {
        // TODO Auto-generated method stub
        List<Image> langs = (List<Image>) repository.findAll();
        for (Image ele:langs)
        {
            if (ele.getImgpath().strip().toLowerCase().equals(name.strip().toLowerCase())) return ele;
        }
        return null;
    }
    
    @Override
    public List<Image> findAllByName(String[] names) {
        // TODO Auto-generated method stub
        ArrayList <Image> re = new ArrayList<Image>();
        for (String name:names)
        {
            re.add(findByName(name)); 
        }
        return re;
    }

    @Override
    public List<Image> findAll() {
        // TODO Auto-generated method stub
        return (List<Image>) repository.findAll();
    }

    @Override
    public List<Image> findAllById(List<Integer> Ids) {
        // TODO Auto-generated method stub
        return (List<Image>) repository.findAllById(Ids);
    }

    @Override
    public Optional<Image> findById(Integer Id) {
        // TODO Auto-generated method stub
        return repository.findById(Id);
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
    public void Save(Image draft) {
        // TODO Auto-generated method stub
        repository.save(draft);
    }

    @Override
    public void saveAll(List<Image> drafts) {
        // TODO Auto-generated method stub
        repository.saveAll(drafts);
    }

    @Override
    public boolean existById(int Id) {
        // TODO Auto-generated method stub
        return repository.existsById(Id);
    }

    

}