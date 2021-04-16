package com.example.plantaseed.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Plant.class, Room.class, RoomWithPlants.class}, version = 1)
public abstract class PlantDatabase extends RoomDatabase {

}
