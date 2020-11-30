package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ListeAnnonce extends AppCompatActivity {
    private ImageView addAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_liste_annonce);
//        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_liste_annonce);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}

        addAnnonce= (ImageView) findViewById(R.id.addAnnonce);
        addAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });
    }

    private void openAddActivity() {
        Intent intent=new Intent(this,AjoutAnnonce.class);
        startActivity(intent);
    }
}