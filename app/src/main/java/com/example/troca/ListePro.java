package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListePro extends AppCompatActivity {
    private ImageView addAnnonce;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Professionnel> myDataSet;
    INodeJS myAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_liste_pro);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}

        //retrofit
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);

        Call<List<Professionnel>> call = myAPI.getPro();

        call.enqueue(new Callback<List<Professionnel>>() {
            @Override
            public void onResponse(Call<List<Professionnel>> call, Response<List<Professionnel>> response) {
                List<Professionnel> Professionnels= response.body();
                for (Professionnel a: Professionnels){
                    String id = a.getIdPro();
                    String nom = a.getNomPrenomPro();
                    String email = a.getEmailPro();
                    String note = a.getNotePro();
                    String adresse = a.getAdressePro();
                    Professionnel professionnel = new Professionnel(id,nom,email,note,adresse);
                    myDataSet.add(professionnel);

                }

            }

            @Override
            public void onFailure(Call<List<Professionnel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



        //retrofit

        recyclerView = findViewById(R.id.my_recycler_view);
        manager = new GridLayoutManager(ListePro.this,2);
        recyclerView.setLayoutManager(manager);
        myDataSet=new ArrayList<>();
        mAdapter = new ProAdapter(ListePro.this,myDataSet);
        recyclerView.setAdapter(mAdapter);



    }
}