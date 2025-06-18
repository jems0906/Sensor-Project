package com.example.myapplication;
import androidx.annotation.Nullable;
import androidx.room.Room;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.widget.Toast;


import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView textView;
    private TextView label;
    private Button exportButton;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.usernameText);
        label = findViewById(R.id.textView);
        exportButton = findViewById(R.id.exportButton);
        exportButton.setOnClickListener(new listener());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sensordatadb").allowMainThreadQueries().build();
    }
    class listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            label.setText("Export button was clicked!");
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_TITLE, "sensordata.csv");
            //OutputStream ostream = getContentResolver().openOutputStream(uri);
            startActivityForResult(intent, 1);
        }
    }
    protected void writeExportFile(OutputStream ostream) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("id,x,y,z");
        SensorDataDao dataDao = db.sensorDataDao();
        List<String> rows = dataDao.allToCsv();
        String rowsStr = String.join("\n", rows);
        sb.append(rowsStr);
        ostream.write(sb.toString().getBytes());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Uri uri = data.getData();
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    writeExportFile(outputStream);
                    outputStream.close();
                } catch (IOException e) {
                    Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            SensorData data = new SensorData();
            data.x = x;
            data.y = y;
            data.z = z;


            SensorDataDao dataDao = db.sensorDataDao();
            dataDao.insertAll(data);

            String sensorText = "Accelerometer:\nX: " + x + "\nY: " + y + "\nZ: " + z;
            textView.setText(sensorText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }
}