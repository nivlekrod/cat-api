package com.gftstart.cat_api.service.impl;

import com.gftstart.cat_api.dto.CatDTO;
import com.gftstart.cat_api.exception.CatBadRequestException;
import com.gftstart.cat_api.exception.CatNotFoundException;
import com.gftstart.cat_api.exception.CatDataAccessException;
import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.repository.CatRepository;
import com.gftstart.cat_api.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

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
        try {
            String imgUrl = getBreedImage(cat.getBreed());
            cat.setReferenceImage(imgUrl);

            return catRepository.save(cat);
        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao salvar no banco de dados: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CatBadRequestException("Erro ao criar gato: " + e.getMessage());
        }
    }

    @Override
    public Cat getCat(Long id) {
        try {
            return catRepository.findById(id).orElseThrow(() -> new CatNotFoundException("Gato não foi encontrado"));
        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cat> getAllCats() {
        try {
            List<Cat> cats = catRepository.findAll();

            if (cats.isEmpty()) {
                throw new CatNotFoundException("Nenhum gato foi encontrado");
            }

            return cats;
        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao acessar o banco de dados" + e.getMessage(), e);
        }
    }

    @Override
    public List<Cat> searchCatsByBreed(String breed) {
        try {
            List<Cat> cats = catRepository.findByBreedIgnoreCase(breed);

            if (cats.isEmpty()) {
                throw new CatNotFoundException("Nenhum gato foi encontrado");
            }

            return cats;
        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public Cat updateCat(Long id, Cat cat) {
        if (!catRepository.existsById(id)) {
            throw new CatNotFoundException("Gato não encontrado para atualização.");
        }

        cat.setId(id);

        String imgUrl = getBreedImage(cat.getBreed());
        cat.setReferenceImage(imgUrl);

        try {
            return catRepository.save(cat);
        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao atualizar o gato no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCat(Long id) {
        try {
            if (!catRepository.existsById(id)) {
                throw new CatNotFoundException("Não foi possível deletar: Gato não encontrado.");
            }

            catRepository.deleteById(id);

        } catch (DataAccessException e) {
            throw new CatDataAccessException("Erro ao acessar o banco de dados" + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getAllBreeds() {
        String url = API_URL + "/breeds";

        try {
            ResponseEntity<List<CatDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CatDTO>>() {
                    }
            );

            if (response.getBody() == null || response.getBody().isEmpty()) {
                throw new CatNotFoundException("Nenhuma raça encontrada");
            }

            return response.getBody().stream()
                    .map(CatDTO::getBreed)
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new CatBadRequestException("Erro ao buscar raças: " + e.getMessage());
        } catch (ResourceAccessException e) {
            throw new CatDataAccessException("Erro de conexão com API externa: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    @Override
    public String getBreedImage(String breed) {
        String url = API_URL + "/breeds";
        String IMG_BASE_URL = "https://cdn2.thecatapi.com/images/";

        try {
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
                    .orElseThrow(() -> new CatNotFoundException("Imagem da raça não encontrada"));

            return IMG_BASE_URL + imgReferenceId + ".jpg";
        } catch (HttpClientErrorException e) {
            throw new CatBadRequestException("Erro ao buscar imagem da raça: " + e.getMessage());
        } catch (ResourceAccessException e) {
            throw new CatDataAccessException("Erro de conexão com API externa: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao buscar imagem: " + e.getMessage(), e);
        }
    }
}