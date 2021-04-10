package com.example.plantaseed.Model;


import androidx.room.Entity;

import com.example.plantaseed.R;
@Entity(tableName = "plant_table")
public class Plant {
    private String name,description, scientificName;
    private int imageURI;


    public Plant(String name, String scientificName, String description) {
        this.name = name;
        this.scientificName = scientificName;
        this.imageURI = R.drawable.ic_launcher_background;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public int getImageURL(){ return imageURI;}

    public String getDescription() {
        return description;
    }


}
