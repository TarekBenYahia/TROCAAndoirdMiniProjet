package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeAnnonce extends AppCompatActivity {
    private ImageView addAnnonce,imageView;
    private List<Annonce> myDataSet;
    INodeJS myAPI;
    private static final String BASEURL="http://192.168.1.15:3000/api/v1/annonce";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imageView= (ImageView) findViewById(R.id.imageView);
        addAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetourAcceuil();
            }
        });


        //Retrofit
        Call<List<Annonce>>call= RetrofitClient2.getINodeJS().getAnnonce();
        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ListeAnnonce.this,"No data available",Toast.LENGTH_LONG).show();
                    return;
                }
                Response<List<Annonce>> aaaa=response;

                RecyclerView myrv = (RecyclerView) findViewById(R.id.my_recycler_view);
                ListAnnonceAdapter myAdapter = new ListAnnonceAdapter(ListeAnnonce.this,response.body());
                myrv.setLayoutManager(new GridLayoutManager(ListeAnnonce.this,2));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
            }
        });


        //retrofit

    }

    private void RetourAcceuil() {
        Intent intent = new Intent(this, ChoixType.class);
        startActivity(intent);
    }

    private void openAddActivity() {
        Intent intent=new Intent(this,AjoutAnnonce.class);
        startActivity(intent);
    }



}