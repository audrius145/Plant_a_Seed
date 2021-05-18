package com.example.plantaseed.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
@Dao
public interface PlantDAO {
    @Insert
    void insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plant_table")
    LiveData<List<Plant>> getAllPlants();

    @Transaction
    @Query("SELECT * FROM plant_table")
    LiveData<List<PlantWithPhotos>> getPlantsWithPhotos();


}
