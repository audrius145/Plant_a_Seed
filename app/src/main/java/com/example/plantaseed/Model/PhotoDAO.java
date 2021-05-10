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
public interface PhotoDAO {
    @Insert
    void insert(Photo photo);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);


    @Query("SELECT * FROM photo_table WHERE photoId =:id")
    Photo findById(int id);

    @Query("SELECT * FROM photo_table")
    LiveData<List<Photo>> getAllPhotos();
}
