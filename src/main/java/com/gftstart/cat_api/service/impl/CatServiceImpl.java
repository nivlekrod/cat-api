package com.gftstart.cat_api.service.impl;

import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.repository.CatRepository;
import com.gftstart.cat_api.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    private static final String API_URL = "https://api.thecatapi.com/v1";

    @Value("${thecatapi.apikey}")
    private String apiKey;

    @Autowired
    private CatRepository catRepository;

    @Override
    public Cat createCat(Cat cat) {
        catRepository.save(cat);
        return cat;
    }

    @Override
    public Cat getCat(Long id) {
        return catRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat cannot be found"));
    }

    @Override
    public List<Cat> getAllCats() {
        List<Cat> cats = catRepository.findAll();
        return cats;
    }

    @Override
    public Cat updateCat(Long id, Cat cat) {
        cat.setId(id);
        catRepository.save(cat);
        return cat;
    }

    @Override
    public String deleteCat(Long id) {
        catRepository.deleteById(id);
        return "Deletado";

    }
}