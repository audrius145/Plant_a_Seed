package com.example.plantaseed.Model;

public class Plant {
    private String name;
    private String scientificName;
    private String opinion;
    private String imageURL;

    public Plant(String name, String scientificName, String opinion, String imageURL) {
        this.name = name;
        this.scientificName = scientificName;
        this.opinion = opinion;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getOpinion() {
        return opinion;
    }

    public String getImageURL(){ return imageURL;}

}
