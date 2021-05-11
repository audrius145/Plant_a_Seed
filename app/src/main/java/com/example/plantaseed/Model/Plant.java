package com.example.plantaseed.Model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "plant_table")
public class Plant {
    @PrimaryKey(autoGenerate = true)
    private int plantId;
    @ForeignKey
            (
                    entity = Room.class,
                    parentColumns = "roomId",
                    childColumns = "id_fkRoom",
                    onDelete = CASCADE
            )
    private int id_fkRoom;

    private String name;

    private String description;

    private String scientificName;

    private String imageURI;


    public Plant(String name, String scientificName, String description, String imageURI) {
        this.name = name;
        this.scientificName = scientificName;
        this.imageURI = imageURI;
        this.description = description;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getId_fkRoom() {
        return id_fkRoom;
    }

    public void setId_fkRoom(int id_fkRoom) {
        this.id_fkRoom = id_fkRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }



}
