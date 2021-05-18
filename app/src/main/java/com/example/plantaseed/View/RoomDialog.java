package com.example.plantaseed.View;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;

import com.example.plantaseed.ViewModel.RoomViewModel;

public class RoomDialog extends AppCompatDialogFragment {
    private EditText nameOfRoom;
    private RoomViewModel roomViewModel;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.layout_roomdialog, null);
        builder.setView(view).setTitle("New Room").setNegativeButton("Cancel", (dialog, which) -> {

        }).setPositiveButton("Ok", (dialog, which) -> {
            String roomName = nameOfRoom.getText().toString();
            roomViewModel.insert(new Room(roomName));
        });
        nameOfRoom = view.findViewById(R.id.room_name);
        return builder.create();
    }

}
