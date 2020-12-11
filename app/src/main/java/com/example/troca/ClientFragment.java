package com.example.troca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClientFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Client> myDataSet;
    INodeJS myAPI;
    private static final String BASEURL = "http://10.0.2.2:3000/cl/client";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        Call<List<Client>> call = myAPI.getClient();
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                List<Client> clients = response.body();
                for (Client c : clients) {
                    String nom = c.getNomPrenomClient();
                    String email = c.getEmailClient();
                    String tel = c.getTelClient();
                    Client client = new Client(nom, email, tel);
                    myDataSet.add(client);
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
            }
        });
        recyclerView = findViewById(R.id.client_recycler_view);
        manager = new GridLayoutManager(ClientFragment.this, 1);
        recyclerView.setLayoutManager(manager);
        myDataSet = new ArrayList<>();
        mAdapter = new MyAdapterClient(ClientFragment.this, myDataSet);
        recyclerView.setAdapter(mAdapter);

 */





        return inflater.inflate(R.layout.fragment_liste_client, container, false);


    }
}
