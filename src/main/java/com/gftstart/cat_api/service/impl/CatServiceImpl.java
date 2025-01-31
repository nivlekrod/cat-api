package com.gftstart.cat_api.service.impl;

import com.gftstart.cat_api.dto.CatDTO;
import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.repository.CatRepository;
import com.gftstart.cat_api.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatServiceImpl implements CatService {

    private static final String API_URL = "https://api.thecatapi.com/v1";

    @Value("${thecatapi.apikey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

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
    public List<Cat> searchCatsByBreed(String breed) {
        List<Cat> cats = catRepository.findByBreedIgnoreCase(breed);
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

    public List<String> getAllBreeds() {
        String url = API_URL + "/breeds";

        ResponseEntity<List<CatDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatDTO>>() {
                }
        );

        return response.getBody().stream()
                .map(CatDTO::getBreed)
                .collect(Collectors.toList());
    }

    public String getBreedImage(String breed) {
        String url = API_URL + "/breeds";
        String IMG_BASE_URL = "https://cdn2.thecatapi.com/images/";

        ResponseEntity<List<CatDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatDTO>>() {
                }
        );

        String imgReferenceId = response.getBody().stream()
                .filter(cat -> breed.equalsIgnoreCase(cat.getBreed()))
                .map(CatDTO::getReferenceImage)
                .findFirst()
                .orElse(null);

        String imgUrl = IMG_BASE_URL + imgReferenceId + ".jpg";

        return imgUrl;
    }
}