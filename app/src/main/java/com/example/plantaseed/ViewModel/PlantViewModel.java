package com.example.plantaseed.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;

import java.util.List;

public class PlantViewModel extends AndroidViewModel {
    PlantRepository repository;
    private LiveData<List<Plant>> allPlants;
    public PlantViewModel(@NonNull Application application) {
        super(application);

        repository = new PlantRepository(application);
        allPlants = repository.getAllPlants();
    }

    public void insert(Plant plant)
    {
        repository.insert(plant);
    }

    public void update(Plant plant)
    {
        repository.update(plant);
    }

    public void delete(Plant plant)
    {
        repository.delete(plant);
    }

    public LiveData<List<Plant>> getAllPlants()
    {
        return allPlants;
    }
}

