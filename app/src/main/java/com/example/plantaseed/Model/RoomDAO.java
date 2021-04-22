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
public interface RoomDAO {


    @Transaction
    @Insert
    void insert(Room room);

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);

    @Query("SELECT * FROM table_room WHERE roomId =:id")
    Room findById(int id);

    @Query("DELETE FROM table_room")
    void deleteAllRooms();

    @Query("SELECT * FROM table_room")
            LiveData<List<Room>> getAllRooms();
    @Transaction
    @Query("SELECT * FROM table_room")
    LiveData<List<RoomWithPlants>> getRoomsWithPlants();



}
