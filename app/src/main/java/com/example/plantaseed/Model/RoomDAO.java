package com.example.plantaseed.Model;

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
    long insertRoom(Room room);

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);

    @Transaction
    @Query("SELECT * FROM table_room")
    List<RoomWithPlants> getUsersWithPlaylists();

}
