package com.example.plantaseed.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantDatabase;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.Model.RoomDAO;
import com.example.plantaseed.Model.RoomWithPlants;

import java.util.List;

public class RoomRepository {
    private RoomDAO roomDAO;
    private LiveData<List<RoomWithPlants>> allRooms;

    public RoomRepository(Application application)
    {
        PlantDatabase database = PlantDatabase.getInstance(application);
        roomDAO = database.roomDAO();
        allRooms = roomDAO.getRoomsWithPlants();
    }

    public void insert(Room room)
    {
        new   RoomRepository.InsertRoomAsyncTask(roomDAO).execute(room);
    }
    public void update(Room room)
    {
        new RoomRepository.UpdateRoomAsyncTask(roomDAO).execute(room);
    }
    public void delete(Room room)
    {
       new RoomRepository.DeleteRoomAsyncTask(roomDAO).execute(room);
    }
    public LiveData<List<RoomWithPlants>> getAllRoomsWithPlants()
    {
        return allRooms;
    }



    private static class InsertRoomAsyncTask extends AsyncTask<Room, Void, Void>
    {
        private RoomDAO roomDAO;

        private InsertRoomAsyncTask(RoomDAO roomDAO)
        {
            this.roomDAO = roomDAO;
        }
        @Override
        protected Void doInBackground(Room... rooms) {
            roomDAO.insert(rooms[0]);
            return null;
        }
    }
    private static class UpdateRoomAsyncTask extends AsyncTask<Room, Void, Void>
    {
        private RoomDAO roomDAO;

        private UpdateRoomAsyncTask(RoomDAO roomDAO)
        {
            this.roomDAO = roomDAO;
        }
        @Override
        protected Void doInBackground(Room... rooms) {
            roomDAO.update(rooms[0]);
            return null;
        }
    }
    private static class DeleteRoomAsyncTask extends AsyncTask<Room, Void, Void>
    {
        private RoomDAO roomDAO;

        private DeleteRoomAsyncTask(RoomDAO roomDAO)
        {
            this.roomDAO = roomDAO;
        }
        @Override
        protected Void doInBackground(Room... rooms) {
            roomDAO.delete(rooms[0]);
            return null;
        }
    }


}
