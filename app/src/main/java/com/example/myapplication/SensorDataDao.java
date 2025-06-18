package com.example.myapplication;
import java.util.List;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Dao;
@Dao

public interface  SensorDataDao {
    @Query("SELECT * FROM sensordata")
    List<SensorData> getAll();

    @Insert
    void insertAll(SensorData... sensorData);

    @Query("SELECT id||','||x||','||y||','||z FROM sensordata")
    List<String> allToCsv();


}
