package com.example.plantaseed.Model;


import androidx.room.Entity;

import com.example.plantaseed.R;
@Entity(tableName = "plant_table")
public class Plant {
    private String name,description, scientificName, imageURI;
    private int image;



    public Plant(String name, String scientificName, String description) {
        this.name = name;
        this.scientificName = scientificName;
        this.image = R.mipmap.ic_image_placeholder_plant;
        this.description = description;
    }
    public Plant(String name, String scientificName, String description, String imageURI) {
        this.name = name;
        this.scientificName = scientificName;
        this.imageURI = imageURI;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public int getImage(){ return image;}

    public String getImageURI(){return imageURI;}
    public String getDescription() {
        return description;
    }


}
