package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class AjoutAnnonce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_ajout_annonce);
        //nomClient=(TextView) findViewById(R.id.nomClient);
        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_ajout_annonce);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
    }
}