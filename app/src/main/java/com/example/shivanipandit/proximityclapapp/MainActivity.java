package com.example.shivanipandit.proximityclapapp;

import android.support.v7.app.AppCompatActivity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    Sensor mobileProximity;
    SensorManager sensorManager;
    MediaPlayer mp3Player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mobileProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {

        super.onResume();

        sensorManager.registerListener(proximityListener, mobileProximity, 3*1000*1000);
    }

    @Override
    protected void onStop() {

        super.onStop();

        if (mp3Player != null) {

            mp3Player.release();
            mp3Player = null;
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(proximityListener);
    }

    private SensorEventListener proximityListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent s) {
            float val = s.values[0];

            if(val < 2f) {

                mp3Player = MediaPlayer.create(getBaseContext(), R.raw.clap);
                mp3Player.start();
            }
            else {

                if(mp3Player != null) {
                    mp3Player.stop();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sr, int accurateRate) {
        }
    };
}
