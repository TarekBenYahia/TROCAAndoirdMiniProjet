package com.example.troca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AcceuilPro extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    INodeJS myAPI;
    private List<CommandesNonValidés> myDataSet;
    TextView nbCommande;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_pro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        nbCommande = (TextView) findViewById(R.id.nbCommande);

        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences sharedPreferences = getSharedPreferences("ProData", MODE_PRIVATE);
        String display = sharedPreferences.getString("display", "");

        try {
            JSONObject p = new JSONObject(display);
            Log.d("", p.getString("idPro"));
            String idP = p.getString("idPro");
            Retrofit retrofit = RetrofitClient.getInstance();
            myAPI = retrofit.create(INodeJS.class);
            Call<List<CommandesNonValidés>> call = myAPI.getCommandes(Integer.parseInt(idP));
            myDataSet = new ArrayList<>();
            recyclerView = findViewById(R.id.my_recycler_view_commande);
            manager = new GridLayoutManager(AcceuilPro.this, 2);
            recyclerView.setLayoutManager(manager);
            call.enqueue(new Callback<List<CommandesNonValidés>>() {
                @Override
                public void onResponse(Call<List<CommandesNonValidés>> call, Response<List<CommandesNonValidés>> response) {
                    try {
                        List<CommandesNonValidés> cnv = response.body();

                        for (CommandesNonValidés a : cnv) {
                            String idC = a.getIdCommande();
                            String idCom = a.getIdClient();
                            String date = a.getDate();
                            String lieu = a.getLieu();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date convertedCurrentDate = sdf.parse(date);
                            Date d = new Date(convertedCurrentDate.getTime() + 86400000);
                            date = sdf.format(d);
                            CommandesNonValidés cmd = new CommandesNonValidés(idC, idCom, date, lieu);
                            myDataSet.add(cmd);
                        }

                        Log.d("Size", String.valueOf(myDataSet.size()));
                        nbCommande.setText(String.valueOf(myDataSet.size()));
                        nbCommande.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ListeCommandesN()).commit();

                            }
                        });
                        mAdapter = new CommandeNAdapter(AcceuilPro.this, myDataSet);
                        recyclerView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<List<CommandesNonValidés>> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


/*

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);
        Call<List<CommandesNonValidés>> call = myAPI.getCommandes(7);
        myDataSet=new ArrayList<>();
        recyclerView = findViewById(R.id.my_recycler_view_commande);
        manager = new GridLayoutManager(AcceuilPro.this,2);
        recyclerView.setLayoutManager(manager);

        call.enqueue(new Callback<List<CommandesNonValidés>>() {
            @Override
            public void onResponse(Call<List<CommandesNonValidés>> call, Response<List<CommandesNonValidés>> response) {
                try {
                    List<CommandesNonValidés> cnv= response.body();
                    for (CommandesNonValidés a: cnv){
                        String idC = a.getIdCommande();
                        String idCom = a.getIdClient();
                        String date = a.getDate();
                        String lieu = a.getLieu();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date convertedCurrentDate = sdf.parse(date);
                        Date d=new Date(convertedCurrentDate.getTime() +86400000);
                        date=sdf.format(d );
                        CommandesNonValidés cmd = new CommandesNonValidés(idC,idCom,date,lieu);
                        myDataSet.add(cmd);


                    }
                    Log.d("Size", String.valueOf(myDataSet.size()));
                    nbCommande.setText( String.valueOf(myDataSet.size()));
                    nbCommande.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new ListeCommandesN()).commit();

                        }
                    });
                    mAdapter = new CommandeNAdapter(AcceuilPro.this,myDataSet);
                    recyclerView.setAdapter(mAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<List<CommandesNonValidés>> call, Throwable t) {

            }
        });

 */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(this, AcceuilPro.class);
                startActivity(intent1);
                break;

            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ProfilPro()).commit();
                break;

            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ListeCommandesN()).commit();
                break;


            case R.id.nav_logout:
                SharedPreferences preferences = getSharedPreferences("ProData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                ProgressDialog dialog = ProgressDialog.show(this, "", "Loading...", true);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

}