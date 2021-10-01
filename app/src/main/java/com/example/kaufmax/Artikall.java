package com.example.kaufmax;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Artikall extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikall);
    }

    public void nadjiArtikal(View v){
        Log.d("uspesno", "uspesno");
    }
}
