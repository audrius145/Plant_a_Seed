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
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class PlantsFragment extends Fragment {
    private ArrayList<Plant> plants;
    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    FloatingActionButton fab;
    String plantJSON;
    public PlantsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);
        createPlantList();
        ///////////////////////////////////////////////////////////////////////////////
        fab = view.findViewById(R.id.plantFab);
        recyclerView = view.findViewById(R.id.parent_recyclerview);
        BuildRecyclerView();

        ///////////////////////////////////////////////////////////////////////////////
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigateToAddNew));


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
        roomAdapter = new RoomAdapter(buildRoomList());
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
    }
    private ArrayList<Room> buildRoomList() {
        ArrayList<Room> roomList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Room room = new Room("Item "+i, plants);
            roomList.add(room);
        }
        return roomList;
    }

    private void createPlantList()
    {
        plants = new ArrayList<>();

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


    }
}