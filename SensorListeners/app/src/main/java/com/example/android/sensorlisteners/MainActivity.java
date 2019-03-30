/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sensorlisteners;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * SensorListeners demonstrates how to gain access to sensors (here, the light
 * and proximity sensors), how to register sensor listeners, and how to
 * handle sensor events.
 */
public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    // System sensor manager instance.
    private SensorManager mSensorManager;

    private Sensor mSensorProximity;
    private Sensor mSensorTemperature;

    private TextView mTextSensorProximity;
    private TextView mTextSensorTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSensorProximity = (TextView) findViewById(R.id.label_proximity);
        mTextSensorTemperature = (TextView) findViewById(R.id.label_temperature);

        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        mSensorProximity = mSensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (mSensorTemperature == null) { mTextSensorTemperature.setText(sensor_error); }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorTemperature != null) {
            mSensorManager.registerListener(this, mSensorTemperature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        int sensorType = sensorEvent.sensor.getType();


        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_PROXIMITY:

                mTextSensorProximity.setText(getResources().getString(
                    R.string.label_proximity, currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:

                mTextSensorTemperature.setText(getResources().getString(
                    R.string.label_temperature, currentValue));
                if (currentValue > 10 && currentValue < 40){
                    mTextSensorTemperature.setTextColor(Color.YELLOW);
                } else if (currentValue > 40){
                    mTextSensorTemperature.setTextColor(Color.RED);
                } else if (currentValue < 10 && currentValue > 0){
                    mTextSensorTemperature.setTextColor(Color.BLUE);
                } else if (currentValue < 0){
                    mTextSensorTemperature.setTextColor(Color.CYAN);
                } else {
                    mTextSensorTemperature.setTextColor(Color.BLACK);
                }

                break;
            default:
                // do nothing
        }
    }

    /**
     * Abstract method in SensorEventListener.  It must be implemented, but is
     * unused in this app.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
