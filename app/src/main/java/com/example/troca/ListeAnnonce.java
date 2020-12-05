package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListeAnnonce extends AppCompatActivity {
    private ImageView addAnnonce;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Annonce> myDataSet;
    INodeJS myAPI;
    private static final String BASEURL="http://10.0.2.2:3000/api/v1/annonce";


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
        //Retrofit
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);

        Call<List<Annonce>> call = myAPI.getAnnonce();
        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                List<Annonce> annonces= response.body();
                for (Annonce a: annonces){
                    String titre = a.getTitreAnnonce();
                    String desc = a.getDescriptionAnnonce();
                    String date = a.getDateAnnonce();
                    Annonce annonce = new Annonce(titre,desc,date,2);
                    myDataSet.add(annonce);

                }



            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



        //retrofit
        recyclerView = findViewById(R.id.my_recycler_view);
        manager = new GridLayoutManager(ListeAnnonce.this,2);
        recyclerView.setLayoutManager(manager);

        myDataSet=new ArrayList<>();



        mAdapter = new MyAdapter(ListeAnnonce.this,myDataSet);
        recyclerView.setAdapter(mAdapter);


    }

    private void openAddActivity() {
        Intent intent=new Intent(this,AjoutAnnonce.class);
        startActivity(intent);
    }



}