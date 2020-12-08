package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AfficheCommentaire extends AppCompatActivity implements CommentaireAdapter.ClickedItem {
    private ArrayList<Comment> comments;

    Toolbar toolbar;
    RecyclerView recyclerView;
    CommentaireAdapter stadeAdapter;
    INodeJS myAPI;
    private EditText comment_text;
    private Button commentSubmit;
    private Annonce complaint;
    Button imageMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_stade);
        imageMore = findViewById(R.id.imageMore);

        recyclerView = findViewById(R.id.recyclerview);
        comment_text = findViewById(R.id.comment_text);
        commentSubmit = findViewById(R.id.commentSubmit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        comments = new ArrayList<>();

        stadeAdapter = new CommentaireAdapter(this::ClickedStade);

        getStades();


    }
    public void getStades(){
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);
        Call<List<Comment>> stadiumslist = myAPI.getCommentaire();

        stadiumslist.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()){
                    List<Comment> stades = response.body();
                    stadeAdapter.setData(stades);
                    recyclerView.setAdapter(stadeAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(AfficheCommentaire.this, "failure", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void ClickedStade(Comment stades) {

        startActivity(new Intent(this,AjoutAnnonce.class).putExtra("data", stades));


    }


    //private void DeleteStade(){}

}
