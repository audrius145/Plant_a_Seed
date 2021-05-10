package com.example.plantaseed.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlantWithPhotos {
    @Embedded
    public Plant plant;

    @Relation(
            parentColumn = "plantId",
            entityColumn = "id_fkPlant"
    )
    public List<Photo> photos;

    public PlantWithPhotos(Plant plant, List<Photo> photos)
    {
        this.plant = plant;
        this.photos = photos;
    }

    public Plant getPlant() {
        return plant;
    }

    public List<Photo> getPhotoList() {
        return photos;
    }
}
