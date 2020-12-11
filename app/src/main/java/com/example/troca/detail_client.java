package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class detail_client extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private Button supprimer;
    private INodeJS myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detail_client);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mTitre=findViewById(R.id.titre);

        mDescription= findViewById(R.id.description);
        mDate= findViewById(R.id.date);
        mImage= findViewById(R.id.imageA);

        Intent intent=getIntent();
        final String nom = intent.getStringExtra("NomPrenomClient");
        final String id=intent.getStringExtra("idClient");
        String email= intent.getStringExtra("emailClient");
        String tel = intent.getStringExtra("telClient");
        if (intent!= null){
            mTitre.setText(nom);
            mDescription.setText(email);
            mDate.setText(tel);
            Glide.with(detail_client.this).load(R.drawable.client).into(mImage);
        }
        supprimer = (Button) findViewById(R.id.supprimerClient);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supprimerUtilisateur(id);
            }
        });
    }

    private void supprimerUtilisateur(final String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(detail_client.this);
        builder.setTitle("Etes vous sur?");
        builder.setMessage("Cette action est irréversible!");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                Retrofit retrofit = RetrofitClient.getInstance();
                myApi=retrofit.create(INodeJS.class);

                Call<Void> call = myApi.supprimer(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Progress();
                        Toast.makeText(detail_client.this, "Client Supprimé", Toast.LENGTH_SHORT).show();
                        redirectDashboard();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            }
        });
        builder.setNegativeButton ("Non",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        AlertDialog ad = builder.create();
        ad.show();

    }

    private void Progress() {
        ProgressDialog dialog = ProgressDialog.show(this,"","Chargement...",true);
    }

    private void redirectDashboard() {
        Intent intent = new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }
}