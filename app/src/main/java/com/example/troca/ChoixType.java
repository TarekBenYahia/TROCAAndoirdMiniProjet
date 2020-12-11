package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static java.lang.Integer.parseInt;

public class ChoixType extends AppCompatActivity {
    private  TextView nomClient ;
    private ImageView trocImg,proImg;
    //String username = getIntent().getStringExtra("USERNAME");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //nomClient=(TextView) findViewById(R.id.nomClient);
        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_choix_type);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}

        trocImg=(ImageView) findViewById(R.id.trocImg);
        proImg=(ImageView) findViewById(R.id.proImg);
        trocImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListeAnnonce();
            }
        });

        proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListePro();
            }
        });

        nomClient = (TextView) findViewById(R.id.nomClient);


        SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");

        try {
            JSONObject  p= new JSONObject(display);
            nomClient.setText( p.getString("NomPrenomClient"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // readFile();
        //System.out.println(username);
        nomClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProfile();
            }
        });




    }

    private void viewProfile() {
        Intent intent=new Intent(this,ProfilClient.class);
        startActivity(intent);
    }
    private void openListePro(){
        Intent intent = new Intent(this, ListePro.class);
        startActivity(intent);
    }

    private void openListeAnnonce() {
        Intent intent= new Intent(this, DetailedAnnonce.class);
        startActivity(intent);
    }


    public void readFile(){
        try {
            FileInputStream fileInputStream=openFileInput("Data.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String lines;
            while ((lines=bufferedReader.readLine())!=null){
                stringBuffer.append(lines+"\n");
            }
            JSONObject p= new JSONObject(stringBuffer.toString());
            nomClient.setText(p.getString("NomPrenomClient"));
           System.out.println(stringBuffer.toString());

        }catch (FileNotFoundException f){
            f.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}