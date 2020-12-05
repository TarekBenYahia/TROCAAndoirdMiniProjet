package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfilClient extends AppCompatActivity {
    private TextView mailC,npC,dnc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profil_client);
        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_profil_client);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        mailC= (TextView) findViewById(R.id.mailC);
        npC= (TextView) findViewById(R.id.npC);
        dnc= (TextView) findViewById(R.id.dnc);

        SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");


        try {
            JSONObject  p= new JSONObject(display);
            mailC.setText( p.getString("emailClient"));
            npC.setText( p.getString("NomPrenomClient"));
           // dnc.setText(p.getString("dateNaissClient"));


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedCurrentDate = sdf.parse(p.getString("dateNaissClient"));
            Date d=new Date(convertedCurrentDate.getTime() +86400000);

            String date=sdf.format(d );
            dnc.setText(date);
            System.out.println(date);

        } catch (JSONException | ParseException e) {;
            e.printStackTrace();
        }



    }
/*
    private void readFile() {
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
            mailC.setText(p.getString("emailClient"));
            npC.setText(p.getString("NomPrenomClient"));
            dnc.setText(p.getString("dateNaissClient"));
            //System.out.println(stringBuffer.toString());s

        }catch (FileNotFoundException f){
            f.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

 */

}