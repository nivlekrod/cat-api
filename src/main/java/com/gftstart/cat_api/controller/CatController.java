package com.gftstart.cat_api.controller;

import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CatController {

    @Autowired
    private CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/cats/{id}")
    public Cat getCat(@PathVariable("id") Long id) {
        return catService.getCat(id);
    }

    @GetMapping("/cats")
    public List<Cat> getAllCats() {
        return catService.getAllCats();
    }

    @GetMapping("/cats/filter")
    public List<Cat> searchCatsByBreed(@RequestParam String breed) {
        return catService.searchCatsByBreed(breed);
    }

    @PostMapping("/cats")
    public Cat createCat(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }

    @PutMapping("/cats/{id}")
    public Cat updateCat(@PathVariable("id") Long id, @RequestBody Cat cat) {
        return catService.updateCat(id, cat);
    }

    @DeleteMapping("/cats/{id}")
    public String deleteCat(@PathVariable("id") Long id) {
        return catService.deleteCat(id);
    }

    @ResponseBody
    @GetMapping("/breeds")
    public List<String> getAllBreeds() {
        return catService.getAllBreeds();
    }

    @GetMapping("/image")
    public String getBreedImage(@RequestParam("breed") String breed){
        return catService.getBreedImage(breed);
    }
}