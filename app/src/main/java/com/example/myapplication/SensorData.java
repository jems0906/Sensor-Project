package com.example.myapplication;

import androidx.room.Entity;

import androidx.room.PrimaryKey;
@Entity
public class SensorData{
    @PrimaryKey(autoGenerate = true)
    public int id;
    public float x;
    public float y;
    public float z;

}




