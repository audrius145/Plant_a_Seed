package com.example.plantaseed.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.PlantViewModel;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;


public class UpdatePlantFragment extends Fragment {
    EditText plantName;
    EditText scientificName;
    EditText plantDesc;
    String plantJSON;
    Spinner spinner;
    Plant updatePlant;
    Button button;
    RoomViewModel roomViewModel;
    PlantViewModel plantViewModel;
    public UpdatePlantFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_update_plant, container, false);
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        plantViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PlantViewModel.class);
        plantName = view.findViewById(R.id.editName);
        plantDesc = view.findViewById(R.id.editDescription);
        scientificName = view.findViewById(R.id.editScience);
        spinner = view.findViewById(R.id.editSpinner);
        button = view.findViewById(R.id.updateButton);
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, rooms);
        spinner.setAdapter(adapter);
        roomViewModel.getAllRooms().observe(getViewLifecycleOwner(), updateRooms -> {
            adapter.clear();
            adapter.addAll(updateRooms);
            adapter.notifyDataSetChanged();
        });



        if (getArguments() != null && getArguments().containsKey("plantObject")) {
            plantJSON = getArguments().getString("plantObject");
            updatePlant = new Gson().fromJson(plantJSON, Plant.class);
            plantName.setText(updatePlant.getName());
            plantDesc.setText(updatePlant.getDescription());
            scientificName.setText(updatePlant.getScientificName());
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                updatePlant.setId_fkRoom(((Room)parent.getSelectedItem()).getRoomId());
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(updatePlant.getId_fkRoom());

        button.setOnClickListener(v -> {

            updatePlant.setDescription(plantDesc.getText().toString());
            updatePlant.setName(plantName.getText().toString());
            updatePlant.setScientificName(scientificName.getText().toString());
            plantViewModel.update(updatePlant);

            Bundle bundle = new Bundle();
            bundle.putString("plantObject", new Gson().toJson(updatePlant));
            Navigation.findNavController(view).navigate(R.id.plantViewFragment,bundle);

        });

       return view;
    }
}