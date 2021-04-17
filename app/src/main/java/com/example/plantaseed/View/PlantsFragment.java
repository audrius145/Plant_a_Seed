package com.example.plantaseed.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.Model.RoomWithPlants;
import com.example.plantaseed.R;

import com.example.plantaseed.ViewModel.PlantViewModel;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class PlantsFragment extends Fragment {
    private ArrayList<Plant> plants;
    private RoomViewModel roomViewModel;
    private PlantViewModel plantViewModel;
    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    FloatingActionButton plantFab;
    FloatingActionButton roomFab;
    String plantJSON;
    public PlantsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        plantViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PlantViewModel.class);
        roomViewModel.getAllRoomsWithPlants().observe(getViewLifecycleOwner(), new Observer<List<RoomWithPlants>>() {
            @Override
            public void onChanged(List<RoomWithPlants> rooms) {
                roomAdapter.setRooms(rooms);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////
        plantFab = view.findViewById(R.id.addPlant);
        roomFab = view.findViewById(R.id.addRoom);
        recyclerView = view.findViewById(R.id.parent_recyclerview);
        BuildRecyclerView();

        ///////////////////////////////////////////////////////////////////////////////
        plantFab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigateToAddNew));


        if (getArguments() != null && getArguments().containsKey("plantObject")) {
            plantJSON = getArguments().getString("plantObject");
            Plant newPlant = new Gson().fromJson(plantJSON, Plant.class);
            plants.add(newPlant);
            roomAdapter.notifyDataSetChanged();
        }
        return view;
    }

    private void BuildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter = new RoomAdapter();
        recyclerView.setAdapter(roomAdapter);
    }


}