package com.example.plantaseed.Model;

import com.example.plantaseed.R;

public class Plant {
    private String name,description, scientificName;
    private int imageURL;


    public Plant(String name, String scientificName, String description) {
        this.name = name;
        this.scientificName = scientificName;
        this.imageURL = R.drawable.ic_launcher_background;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public int getImageURL(){ return imageURL;}

    public String getDescription() {
        return description;
    }


}
