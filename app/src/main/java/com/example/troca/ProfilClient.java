package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Retrofit;

public class ProfilClient extends AppCompatActivity {
    private TextView mailC,npC,dnc;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_profil_client);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        mailC= (TextView) findViewById(R.id.mailC);
        npC= (TextView) findViewById(R.id.npC);
        dnc= (TextView) findViewById(R.id.dnc);
        imageView = findViewById(R.id.imageView3);
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
            Log.d("photo",p.getString("CinClient"));
            Retrofit retrofit = RetrofitClient.getInstance();
            String url = String.valueOf(retrofit.baseUrl());
            Glide.with(this)
                    .load(url+"cl/uploads/"+p.getString("CinClient"))
                    .override(400,400)
                    .into(imageView);

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