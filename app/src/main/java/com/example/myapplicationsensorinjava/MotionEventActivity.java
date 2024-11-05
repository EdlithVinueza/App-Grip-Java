package com.example.myapplicationsensorinjava;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;

public class MotionEventActivity extends ComponentActivity {

    // Declarar variables para el diseño principal y las vistas de texto
    private RelativeLayout layoutPrincipal;
    private TextView textoPresion;
    private TextView textoTamaño;
    private TextView textoFuerza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);

        // Inicializar las vistas
        layoutPrincipal = findViewById(R.id.motionEventLayout);
        textoPresion = findViewById(R.id.pressureTextView);
        textoTamaño = findViewById(R.id.sizeTextView);
        textoFuerza = findViewById(R.id.forceTextView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                // Obtener los valores de presión y tamaño
                float presion = event.getPressure();
                float tamaño = event.getSize();
                float fuerza = presion * tamaño; // Calcular la fuerza

                // Actualizar los TextViews con los valores obtenidos
                textoPresion.setText("Presión: " + presion + " N/m²");
                textoTamaño.setText("Tamaño: " + tamaño);
                textoFuerza.setText("Fuerza: " + fuerza + " N");

                // Obtener la posición del evento táctil
                float x = event.getX();
                float y = event.getY();
                int color = obtenerColorSegunPosicion(x, y);
                layoutPrincipal.setBackgroundColor(color);
            }
        }
        return super.onTouchEvent(event);
    }

    // Determinar el color basado en la posición del evento táctil
    private int obtenerColorSegunPosicion(float x, float y) {
        if (x < layoutPrincipal.getWidth() / 2 && y < layoutPrincipal.getHeight() / 2) {
            return Color.RED;
        } else if (x >= layoutPrincipal.getWidth() / 2 && y < layoutPrincipal.getHeight() / 2) {
            return Color.GREEN;
        } else if (x < layoutPrincipal.getWidth() / 2 && y >= layoutPrincipal.getHeight() / 2) {
            return Color.BLUE;
        } else {
            return Color.YELLOW;
        }
    }
}