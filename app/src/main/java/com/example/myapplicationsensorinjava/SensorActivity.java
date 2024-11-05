package com.example.myapplicationsensorinjava;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import android.os.Handler;
import android.os.Looper;

public class SensorActivity extends ComponentActivity implements SensorEventListener {

    // Declaración de variables para los elementos de la interfaz de usuario
    private RelativeLayout mainLayout;
    private TextView accelerometerTextView;
    private TextView gyroscopeTextView;
    private TextView forceTextView;
    private TextView proximityTextView;

    // Declaración de variables para el manejo de sensores
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor proximitySensor;
    private Sensor pressureSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        // Inicialización de los elementos de la interfaz de usuario
        mainLayout = findViewById(R.id.sensorLayout);
        accelerometerTextView = findViewById(R.id.accelerometerTextView);
        gyroscopeTextView = findViewById(R.id.gyroscopeTextView);
        forceTextView = findViewById(R.id.forceTextView);
        proximityTextView = findViewById(R.id.proximityTextView);

        // Inicialización del sensor manager y los sensores
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar los listeners para los sensores
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar los listeners para los sensores
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event != null) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    // Obtener los valores del acelerómetro
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    accelerometerTextView.setText("Acelerómetro: x=" + x + "m/s², y=" + y + "m/s², z=" + z + "m/s²");

                    // Calcular la fuerza y actualizar el TextView y el color de fondo
                    float force = (float) Math.sqrt(x * x + y * y + z * z);
                    forceTextView.setText("Fuerza: " + force + "N");
                    mainLayout.setBackgroundColor(obtenerColorSegunFuerza(force));
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    // Obtener los valores del giroscopio
                    x = event.values[0];
                    y = event.values[1];
                    z = event.values[2];
                    gyroscopeTextView.setText("Giroscopio: x=" + x + "rad/s, y=" + y + "rad/s, z=" + z + "rad/s");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    // Obtener el valor del sensor de proximidad
                    float proximity = event.values[0];
                    proximityTextView.setText("Proximidad: " + proximity + "cm");
                    int color = obtenerColorSegunProximidad(proximity);
                    if (color != -1) {
                        mainLayout.setBackgroundColor(color);
                    }
                    break;
            }
        }
    }

    // Método para obtener el color basado en la fuerza
    private int obtenerColorSegunFuerza(float force) {
        int color;
        if (force < 5) {
            color = Color.GREEN;
        } else if (force < 10) {
            color = Color.TRANSPARENT;
        } else if (force < 15) {
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }

        // Mantener el color en pantalla durante más tiempo
        new Handler(Looper.getMainLooper()).postDelayed(() -> mainLayout.setBackgroundColor(Color.TRANSPARENT), 5000);

        return color;
    }

    // Método para obtener el color basado en la proximidad
    private int obtenerColorSegunProximidad(float proximity) {
        return proximity < 5 ? Color.MAGENTA : -1;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No hacer nada
    }
}