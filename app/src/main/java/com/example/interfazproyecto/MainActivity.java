package com.example.interfazproyecto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

import processing.android.CompatUtils;
import processing.android.PFragment;

public class MainActivity extends AppCompatActivity {
    //public PApplet sketch;
    String boton;
    Intent intent;
    public Sketch parametros;
    Timer timer;
    TimerTask task;
    int Nhormigas = 1;
    int DirBlanca=1;
    int DirNegra=3;
    int hormigasx = 20;
    int hormigasy = 20;
    int hormigasz = 2;
    int hormigas[][] = new int [1][3];

    int M,N;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        int[] dir= { DirBlanca,DirNegra};

        hormigas[0][0]=hormigasx;
        hormigas[0][1]=hormigasy;
        hormigas[0][2]=hormigasz;

        parametros = new Sketch(Nhormigas,hormigas,dir);
        PFragment fragment = new PFragment(parametros);
        fragment.setView(frame, this);

        boton=parametros.Cbutton;
        timer = new Timer(true);
        task = new TimerTask() {
            @Override
            public void run() {
                boton=parametros.Cbutton;
            }
        };
        timer.scheduleAtFixedRate(task, 500, 1000);  //Se corre la tarea a una taza fija de 100 ms, desde aqui se inicia la tarea


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (parametros != null) {
            parametros.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (parametros != null) {
            parametros.onNewIntent(intent);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("cicloVida", "Metodo onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("cicloVida", "Metodo onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("cicloVida", "Metodo onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("cicloVida", "Metodo onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("cicloVida", "Metodo onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("cicloVida", "Metodo onRestart");
    }


}