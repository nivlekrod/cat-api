package com.gftstart.cat_api.repository;

import com.gftstart.cat_api.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByBreedIgnoreCase(String breed);
}