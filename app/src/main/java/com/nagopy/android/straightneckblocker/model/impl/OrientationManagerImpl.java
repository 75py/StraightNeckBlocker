package com.nagopy.android.straightneckblocker.model.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.nagopy.android.straightneckblocker.model.OrientationManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class OrientationManagerImpl implements OrientationManager, SensorEventListener {

    @Inject
    Context context;
    @Inject
    SensorManager sensorManager;

    Sensor accelerometerSensor;
    Sensor magneticSensor;

    private static final int BATCH_LATENCY_3s = 3000000;
    private static final int MATRIX_SIZE = 16;
    private static final int DIMENSION = 3;

    float[] accelermometerValues = null;
    float[] magneticFieldValues = null;

    @Inject
    OrientationManagerImpl() {
    }

    public void init() {
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void resume() {
        boolean acc = sensorManager.registerListener(this, accelerometerSensor
                , SensorManager.SENSOR_DELAY_UI
                , BATCH_LATENCY_3s);
        final boolean mag = sensorManager.registerListener(this, magneticSensor
                , SensorManager.SENSOR_DELAY_UI
                , BATCH_LATENCY_3s);
        Timber.d("acc=%s, mag=%s", acc, mag);
    }

    public void pause() {
        sensorManager.unregisterListener(this);
    }

    public void destroy() {
    }

    @Override
    public boolean ready() {
        return accelermometerValues != null && magneticFieldValues != null;
    }

    @Override
    public double getPatch() {
        if (!ready()) {
            throw new IllegalStateException();
        }

        float[] rotationMatrix = new float[MATRIX_SIZE];
        float[] inclinationMatrix = new float[MATRIX_SIZE];
        float[] remapedMatrix = new float[MATRIX_SIZE];
        float[] orientationValues = new float[DIMENSION];
        SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelermometerValues, magneticFieldValues);
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remapedMatrix);
        SensorManager.getOrientation(remapedMatrix, orientationValues);
        return radianToDegrees(orientationValues[1]);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                // accelermometerValues = event.values.clone();
                accelermometerValues = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                // magneticFieldValues = event.values.clone();
                magneticFieldValues = event.values;
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * ラジアンを角度に変換する
     *
     * @param angrad ラジアン
     * @return 角度
     */
    private double radianToDegrees(float angrad) {
        Timber.d("radian=%s", angrad);
        return Math.floor(angrad >= 0 ? Math.toDegrees(angrad) : 360 + Math.toDegrees(angrad));
    }

}
