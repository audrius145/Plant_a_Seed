package com.example.plantaseed.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantDAO;
import com.example.plantaseed.Model.PlantDatabase;

import java.util.List;

public class PlantRepository {
    private PlantDAO plantDAO;
    private LiveData<List<Plant>> allPlants;

    public PlantRepository(Application application)
    {
        PlantDatabase database = PlantDatabase.getInstance(application);
        plantDAO = database.plantDAO();
        allPlants = plantDAO.getAllPlants();
    }

    public void insert(Plant plant)
    {
        new InsertPlantAsyncTask(plantDAO).execute(plant);
    }
    public void update(Plant plant)
    {
        new UpdatePlantAsyncTask(plantDAO).execute(plant);
    }
    public void delete(Plant plant)
    {
        new DeletePlantAsyncTask(plantDAO).execute(plant);
    }
    public LiveData<List<Plant>> getAllPlants()
    {
        return allPlants;
    }

    private static class InsertPlantAsyncTask extends AsyncTask<Plant, Void, Void>
    {
        private PlantDAO plantDAO;

        private InsertPlantAsyncTask(PlantDAO plantDAO)
        {
            this.plantDAO = plantDAO;
        }
        @Override
        protected Void doInBackground(Plant... plants) {
            plantDAO.insert(plants[0]);
            return null;
        }
    }
    private static class UpdatePlantAsyncTask extends AsyncTask<Plant, Void, Void>
    {
        private PlantDAO plantDAO;

        private UpdatePlantAsyncTask(PlantDAO plantDAO)
        {
            this.plantDAO = plantDAO;
        }
        @Override
        protected Void doInBackground(Plant... plants) {
            plantDAO.insert(plants[0]);
            return null;
        }
    }
    private static class DeletePlantAsyncTask extends AsyncTask<Plant, Void, Void>
    {
        private PlantDAO plantDAO;

        private DeletePlantAsyncTask(PlantDAO plantDAO)
        {
            this.plantDAO = plantDAO;
        }
        @Override
        protected Void doInBackground(Plant... plants) {
            plantDAO.insert(plants[0]);
            return null;
        }
    }



}
