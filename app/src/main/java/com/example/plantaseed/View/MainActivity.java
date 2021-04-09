package com.example.plantaseed.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    PlantAdapter plantAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        recyclerView = findViewById(R.id.recyclerPlant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        plantAdapter = new PlantAdapter(getPlantList());
        recyclerView.setAdapter(plantAdapter);
        setupNavigation();
    }
    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomnav);
        toolbar = findViewById(R.id.toolbar);

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

    private void setupNavigation()
    {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


}
