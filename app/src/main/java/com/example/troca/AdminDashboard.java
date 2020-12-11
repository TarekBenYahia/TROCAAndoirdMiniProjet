package com.example.troca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Client> myDataSett;
    INodeJS myAPI;
    private static final String BASEURL="http://10.0.2.2:3000/cl/client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //retrofit



        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);
        myDataSett=new ArrayList<>();
        recyclerView = findViewById(R.id.client_my_recycler_view);
        manager = new GridLayoutManager(AdminDashboard.this,1);
        recyclerView.setLayoutManager(manager);



        Call<List<Client>> call = myAPI.getClient();
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                List<Client> clients= response.body();
                for (Client c: clients){
                    String id =c.getIdClient();
                    String nom = c.getNomPrenomClient();
                    String email = c.getEmailClient();
                    String tel = c.getTelClient();
                    Client client = new Client(id,nom,email,tel,2);

                    myDataSett.add(client);
                    Log.d("","Printed"+c.getEmailClient());

                }
                Log.d("size", String.valueOf(myDataSett.size()));
                mAdapter = new MyAdapterClient(AdminDashboard.this,myDataSett);
                recyclerView.setAdapter(mAdapter);
            }


            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new FragmentClient()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
        super.onBackPressed();}
    }
}