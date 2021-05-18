package com.example.plantaseed.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import com.example.plantaseed.Model.Plant;

import com.example.plantaseed.Model.RoomWithPlants;
import com.example.plantaseed.R;

import com.example.plantaseed.ViewModel.PlantViewModel;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PlantsFragment extends Fragment {
    private RoomViewModel roomViewModel;
    private PlantViewModel plantViewModel;

    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    FloatingActionButton plantFab;
    FloatingActionButton roomFab;
    String plantJSON;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        plantViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PlantViewModel.class);
        roomViewModel.getAllRoomsWithPlants().observe(getViewLifecycleOwner(), rooms -> roomAdapter.setRooms(rooms));
        ///////////////////////////////////////////////////////////////////////////////
        plantFab = view.findViewById(R.id.addPlant);
        roomFab = view.findViewById(R.id.addRoom);
        recyclerView = view.findViewById(R.id.parent_recyclerview);
        BuildRecyclerView();
        ///////////////////////////////////////////////////////////////////////////////
        plantFab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigateToAddNew));
        roomFab.setOnClickListener(v -> openDialog());
        if (getArguments() != null && getArguments().containsKey("plantObject")) {
            plantJSON = getArguments().getString("plantObject");
            Plant newPlant = new Gson().fromJson(plantJSON, Plant.class);
            plantViewModel.insert(newPlant);
            roomAdapter.notifyDataSetChanged();
        }
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.check_dialog, null);
        mBuilder.setTitle("You are deleting an item!");
        mBuilder.setMessage("Are you sure?");
        mBuilder.setView(mView);



        return view;
    }

    private void BuildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter = new RoomAdapter(new PlantAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Plant plant, View view) {
                Bundle bundle = new Bundle();
                bundle.putString("plantObject", new Gson().toJson(plant));
                Navigation.findNavController(view).navigate(R.id.plantViewFragment, bundle);
            }

            @Override
            public void onDeleteClick(Plant plant, int position) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            deletePlant(plant,position);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };

                AlertDialog.Builder builder = buildDialog(dialogClickListener);
                AlertDialog alertDialog = builder.create();
                if(getDialogStatus()){
                    alertDialog.hide();
                    deletePlant(plant,position);
                }else{
                    alertDialog.show();
                }

            }
        }, room -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                       deleteRoom(room);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };
            AlertDialog.Builder builder = buildDialog(dialogClickListener);
            AlertDialog alertDialog = builder.create();
            if(getDialogStatus()){
                alertDialog.hide();
                deleteRoom(room);
            }else{
                alertDialog.show();
            }

    });
        recyclerView.setAdapter(roomAdapter);
    }

    public void openDialog()
    {
        RoomDialog roomDialog = new RoomDialog();
        roomDialog.show(getActivity().getSupportFragmentManager(),"room dialog");
    }

    public AlertDialog.Builder buildDialog(DialogInterface.OnClickListener clickListener)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.check_dialog, null);
        CheckBox mCheckBox = mView.findViewById(R.id.checkBox);
        mBuilder.setTitle("You are deleting an item!");
        mBuilder.setMessage("Are you sure?");
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("Yes", clickListener);
        mBuilder.setNegativeButton("No", clickListener);
        mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> storeDialogStatus(compoundButton.isChecked()));
        return mBuilder;
    }

    private void storeDialogStatus(boolean isChecked){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus(){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    private void deleteRoom(RoomWithPlants room)
    {
        List<Plant> plants = room.plants;
        for(Plant plant : plants)
        {
            plant.setId_fkRoom(1);
            plantViewModel.update(plant);
        }
        roomViewModel.delete(room.room);
        roomAdapter.notifyDataSetChanged();
    }

    private void deletePlant(Plant plant, int position)
    {
        plantViewModel.delete(plant);
        roomAdapter.notifyItemRemoved(position);
    }







}