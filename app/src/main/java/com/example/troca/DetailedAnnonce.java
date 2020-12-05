package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DetailedAnnonce extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detailed_annonce);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mTitre= findViewById(R.id.toolbar);
        mDescription= findViewById(R.id.description);
        mDate= findViewById(R.id.date);
        mImage= findViewById(R.id.imageA);

        Intent intent=getIntent();
        String title = intent.getStringExtra("titreAnnonce");
        String desc= intent.getStringExtra("descriptionAnnonce");
        String date = intent.getStringExtra("dateAnnonce");
        if (intent!= null){
            mTitre.setText(title);
            mDescription.setText(desc);
            mDate.setText(date);
            Glide.with(DetailedAnnonce.this).load(R.drawable.client).into(mImage);
        }
    }
}