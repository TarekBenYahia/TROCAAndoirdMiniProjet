package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
import com.example.troca.adapters.CommandesFiniesAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommandesFinies extends AppCompatActivity {
    INodeJS myAPI;
    private List<CommandesNonValidés> myDataSet;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_commandes_finies);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String display = sharedPreferences.getString("display", "");

        try {
            JSONObject p = new JSONObject(display);
            Log.d("", p.getString("idClient"));
            String idC = p.getString("idClient");
            Retrofit retrofit = RetrofitClient.getInstance();
            myAPI = retrofit.create(INodeJS.class);
            Call<List<CommandesNonValidés>> call = myAPI.getCommandesClientValides(Integer.parseInt(idC));
            myDataSet = new ArrayList<>();
            recyclerView = findViewById(R.id.my_recycler_view_commandeFinie);
            manager = new GridLayoutManager(CommandesFinies.this, 1);
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
                            String idPro = a.getIdPro();
                            String note = a.getNote();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date convertedCurrentDate = sdf.parse(date);
                            Date d = new Date(convertedCurrentDate.getTime() + 86400000);
                            date = sdf.format(d);
                            CommandesNonValidés cmd = new CommandesNonValidés(idC, idCom,idPro, date, lieu,note);
                            myDataSet.add(cmd);


                        }
                        Log.d("Size", String.valueOf(myDataSet.size()));
                        mAdapter = new CommandesFiniesAdapter(CommandesFinies.this, myDataSet);
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


    }
}