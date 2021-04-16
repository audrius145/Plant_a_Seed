package com.example.plantaseed.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface PlantDAO {
    @Insert
    void insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plant_table ORDER BY name DESC")
    LiveData<List<Plant>> getAllPlants();
}
