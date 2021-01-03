package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Chargement extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    Animation topAnim,BottomAnim;
    ImageView image;
    TextView text,troc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_chargement);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e)
        {}
        ConstraintLayout constraintLayout = findViewById(R.id.layoutBg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        BottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.logoC);
        text = findViewById(R.id.troc);
        troc2 = findViewById(R.id.troc2);

        image.setAnimation(topAnim);
        text.setAnimation(BottomAnim);
        troc2.setAnimation(BottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
                String display = sharedPreferences.getString("display","");

                SharedPreferences sharedPreferencesP = getSharedPreferences("ProData",MODE_PRIVATE);
                String proDisplay = sharedPreferencesP.getString("display","");

                if(display.length() != 0)
                {
                    Intent intent = new Intent(Chargement.this,ChoixType.class);
                    startActivity(intent);
                }
                else if (proDisplay.length()!= 0 ){
                    Intent intent = new Intent(Chargement.this,AcceuilPro.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(Chargement.this,MainActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        },SPLASH_SCREEN);

    }



}