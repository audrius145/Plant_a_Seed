package com.example.plantaseed.Model;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Plant.class, com.example.plantaseed.Model.Room.class, Photo.class}, version = 1, exportSchema = false)
public abstract class PlantDatabase extends RoomDatabase {
    private static PlantDatabase instance;

    public abstract PlantDAO plantDAO();
    public abstract RoomDAO roomDAO();
    public abstract PhotoDAO photoDAO();

    public static synchronized PlantDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PlantDatabase.class,
                    "plant_a_seed_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(() -> getInstance(context).roomDAO().insert(new com.example.plantaseed.Model.Room("Roomless")));
                        }
                    }).build();


        }


    return instance;
    }



}
