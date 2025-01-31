package com.gftstart.cat_api.service;

import com.gftstart.cat_api.model.Cat;

import java.util.List;

public interface CatService {
    Cat createCat(Cat cat);

    Cat getCat(Long id);

    List<Cat> getAllCats();

    List<Cat> searchCatsByBreed(String breed);

    Cat updateCat(Long id, Cat cat);

    String deleteCat(Long id);

    List<String> getAllBreeds();

    String getBreedImage(String breed);
}