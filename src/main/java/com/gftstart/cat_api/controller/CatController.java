package com.gftstart.cat_api.controller;

import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Cat> getCat(@PathVariable("id") Long id) {
        Cat cat = catService.getCat(id);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/cats")
    public ResponseEntity<List<Cat>> getAllCats() {
        List<Cat> cats = catService.getAllCats();
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/cats/filter")
    public ResponseEntity<List<Cat>> searchCatsByBreed(@RequestParam String breed) {
        List<Cat> cats = catService.searchCatsByBreed(breed);
        return ResponseEntity.ok(cats);
    }

    @PostMapping("/cats")
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat createdCat = catService.createCat(cat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCat);
    }

    @PutMapping("/cats/{id}")
    public ResponseEntity<Cat> updateCat(@PathVariable("id") Long id, @RequestBody Cat cat) {
        Cat updatedCat = catService.updateCat(id, cat);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/cats/{id}")
    public ResponseEntity<String> deleteCat(@PathVariable("id") Long id) {
        catService.deleteCat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseBody
    @GetMapping("/breeds")
    public ResponseEntity<List<String>> getAllBreeds() {
        List<String> breeds = catService.getAllBreeds();
        return ResponseEntity.ok(breeds);
    }

    @GetMapping("/image")
    public ResponseEntity<String> getBreedImage(@RequestParam("breed") String breed) {
        String imageUrl = catService.getBreedImage(breed);
        return ResponseEntity.ok(imageUrl);
    }
}