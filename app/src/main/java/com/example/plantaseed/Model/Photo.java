package com.example.plantaseed.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "photo_table")
public class Photo {



    @PrimaryKey(autoGenerate = true)
    private int photoId;

    @ForeignKey
            (
                    entity = Plant.class,
                    parentColumns = "plantId",
                    childColumns = "id_fkPlant",
                    onDelete = CASCADE
            )
    private int id_fkPlant;


    public int getId_fkPlant() {
        return id_fkPlant;
    }

    public void setId_fkPlant(int id_fkPlant) {
        this.id_fkPlant = id_fkPlant;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    private String photoPath;

    public Photo(String photoPath)
    {
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

}
