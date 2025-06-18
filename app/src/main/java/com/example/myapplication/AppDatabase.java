package com.example.myapplication;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { SensorData.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SensorDataDao sensorDataDao();

}
