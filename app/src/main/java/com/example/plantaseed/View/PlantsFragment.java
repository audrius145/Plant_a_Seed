package com.example.plantaseed.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlantsFragment extends Fragment {
    RecyclerView recyclerView;
    PlantAdapter plantAdapter;
    FloatingActionButton fab;

    public PlantsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);

        ///////////////////////////////////////////////////////////////////////////////
        fab = view.findViewById(R.id.plantFab);
        recyclerView = view.findViewById(R.id.recyclerPlant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        plantAdapter = new PlantAdapter(getPlantList());
        recyclerView.setAdapter(plantAdapter);
        ///////////////////////////////////////////////////////////////////////////////
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigateToAddNew));
        return view;
    }




    private ArrayList<Plant> getPlantList()
    {
        ArrayList<Plant> plants = new ArrayList<>();

        Plant a = new Plant("Leafy mcLeaf", "Leafy lefiosa", "Big chunky leaves" );
        plants.add(a);
        Plant b = new Plant("Jade plant", "Crassula ovata", "Big chunky leaves" );
        plants.add(b);
        Plant c = new Plant("Wild Rose", "Rosa Rugosa", "Smells good" );
        plants.add(c);
        Plant d = new Plant("Rapeseed", "Brassica Napus", "Smells bad" );
        plants.add(d);
        Plant e = new Plant("Chinese money plant", "Pilea peperomioides", "Doesnt grow money" );
        plants.add(e);
        Plant f = new Plant("Devils ivy", "Epipremnum aureum", "Stupid ivy" );
        plants.add(f);
        Plant g = new Plant("Ghost Plant", "Monotropa uniflora", "Call the ghost busters" );
        plants.add(g);
        Plant h = new Plant("Dutchmans pipevine", "Aristolochia tomentosa", "Its pretty toxic" );
        plants.add(h);

        return plants;
    }
}