package com.example.kaufmax;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Meni extends AppCompatActivity {

    ActionBar actionBar;
    Button artikalbtn;
    Button rafbtn;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meni);
        context = this;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void ArtikalFun(View view){
        Intent intent = new Intent(this, Artikal.class);
        startActivity(intent);
    }
    public void RafFun(View view){
        Intent intent = new Intent(this, Raf.class);
        startActivity(intent);
    }
    public void PaketFun(View view){
        Intent intent = new Intent(this, Paket.class);
        startActivity(intent);
    }
    public void Izloguj(View view){
        new AlertDialog.Builder(this)
                .setTitle("IZLOGUJ SE")
                .setMessage("Želite da se izlogujete?")
                .setNegativeButton("NE", null)
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(context, MainActivity.class);
                        startActivity(a);
                    }
                }).create().show();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
}