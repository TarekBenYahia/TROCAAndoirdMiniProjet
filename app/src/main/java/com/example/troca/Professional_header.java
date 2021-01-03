package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Professional_header extends AppCompatActivity {
    TextView nom_pro, email_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_professional_header);
        SharedPreferences sharedPreferences = getSharedPreferences("ProData", MODE_PRIVATE);
        String display = sharedPreferences.getString("display", "");
        nom_pro = (TextView) findViewById(R.id.email_pro);

        try {
            JSONObject p = new JSONObject(display);
            nom_pro.setText(p.getString("NomPrenomPro"));
            Log.d("", p.getString("NomPrenomPro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}