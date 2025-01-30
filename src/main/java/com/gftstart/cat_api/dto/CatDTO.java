package com.gftstart.cat_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatDTO {

    private String id;

    @JsonProperty("name")
    private String breed;

    @JsonProperty("reference_image_id")
    private String referenceImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    @Override
    public String toString() {
        return "CatDTO {" +
                "breed='" + breed + '\'' +
                ", referenceImage='" + referenceImage + '\'' +
                '}';
    }
}
