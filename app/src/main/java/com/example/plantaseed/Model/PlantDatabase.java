package com.example.plantaseed.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Plant.class, com.example.plantaseed.Model.Room.class}, version = 2, exportSchema = false)
public abstract class PlantDatabase extends RoomDatabase {
    private static PlantDatabase instance;

    public abstract PlantDAO plantDAO();
    public abstract RoomDAO roomDAO();

    public static synchronized PlantDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PlantDatabase.class,
                    "plant_a_seed_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
    return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private RoomDAO roomDAO;

        private PopulateDbAsyncTask(PlantDatabase db)
        {
            roomDAO = db.roomDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDAO.insert(new com.example.plantaseed.Model.Room("Toilet"));
            roomDAO.insert(new com.example.plantaseed.Model.Room("Unknown"));
            roomDAO.insert(new com.example.plantaseed.Model.Room("Living Room"));
            return null;
        }
    }

}
