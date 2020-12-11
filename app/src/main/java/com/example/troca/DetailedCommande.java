package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailedCommande extends AppCompatActivity {
    private Button btnRefuser,BtnAccepter;
    private TextView mEmail,mDate,mLieu;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detailed_commande);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mEmail= findViewById(R.id.emailC);
        mDate= findViewById(R.id.dateC);
        mLieu= findViewById(R.id.lieuC);
        btnRefuser = (Button) findViewById(R.id.BtnRefuser);
        BtnAccepter = (Button) findViewById(R.id.BtnAccepter);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);



        Intent intent=getIntent();

        final String email = intent.getStringExtra("emailC"); // id****
        final String date= intent.getStringExtra("dateC");
        final String lieu = intent.getStringExtra("lieuC");
        final String idCommande = intent.getStringExtra("idC");
        BtnAccepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accepterCommande(idCommande);
            }
        });
        btnRefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    SharedPreferences sharedPreferences= getSharedPreferences("ProData",MODE_PRIVATE);
                    String display = sharedPreferences.getString("display","");

                    JSONObject  p= new JSONObject(display);
                    String idPro = p.getString("idPro");
                    supprimerCommande(idCommande);
                    refuserCommande(idCommande,email,idPro,date,lieu);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        if (intent!= null){
            getMailC(email);
            mLieu.setText(date);
            mDate.setText(lieu);

        }
    }

    private void getMailC(String id) {
        String mail;
        compositeDisposable.add(myAPI.getmail(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                JSONObject p= new JSONObject(s);

                String mailc = p.getString("emailClient");
                mEmail.setText(mailc);
            }
        }));
    }
    private void accepterCommande (final String id) {
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI=retrofit.create(INodeJS.class);
                Call<Void> call = myAPI.accepterCommande(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Progress();
                        Toast.makeText(DetailedCommande.this, "Commande Acceptée", Toast.LENGTH_SHORT).show();
                        redirectAcceuilPro();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
        }

    private void Progress() {
        ProgressDialog dialog = ProgressDialog.show(this,"","Chargement...",true);
    }
    private void redirectAcceuilPro() {
        Intent intent = new Intent(this,AcceuilPro.class);
        startActivity(intent);
    }

    private void refuserCommande(String idCommande, String idClient, String idPro, String date, String lieu) {
        compositeDisposable.add(myAPI.refuserCommande(idCommande,idClient,idPro,date,lieu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(DetailedCommande.this, ""+s, Toast.LENGTH_SHORT).show();
                        Progress();
                        redirectAcceuilPro();

                    }
                })

        );
    }
    private void supprimerCommande(String id)
    {
        Call<Void> call = myAPI.supprimerCmdR(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }





}

