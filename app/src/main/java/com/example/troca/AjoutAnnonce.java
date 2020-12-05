package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class AjoutAnnonce extends AppCompatActivity {

    INodeJS myApi5;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Button buttonAjoutAnnonce;
    EditText titreAnnonce,catAnnonce,photoAnnonce,descAnnonce,clientAnn;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_2);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_ajout_annonce);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi5= retrofit.create(INodeJS.class);
        //view
        buttonAjoutAnnonce = (Button) findViewById(R.id.buttonAjoutAnnonce);
        titreAnnonce = (EditText) findViewById(R.id.titreAnnonce);
        catAnnonce = (EditText) findViewById(R.id.catAnnonce);
        photoAnnonce = (EditText) findViewById(R.id.photoAnnonce);

        descAnnonce = (EditText) findViewById(R.id.descAnnonce);
        clientAnn = (EditText) findViewById(R.id.clientAnn);

        //EVENT
        buttonAjoutAnnonce.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idc=parseInt(clientAnn.getText().toString(),10);
                int cat = parseInt( catAnnonce.getText().toString(),10);

                ajoutAnnonce(
                        titreAnnonce.getText().toString(),
                        descAnnonce.getText().toString(),
                        photoAnnonce.getText().toString(),

                        idc,
                        cat
                );
                Toast.makeText(AjoutAnnonce.this, "Annonce Ajoutée Avec succès", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void ajoutAnnonce(String titreAnnonce, String descriptionAnnonce, String photoAnnonce, int idClient, int idCategorie) {
        compositeDisposable.add(myApi5.ajoutAnnonce(titreAnnonce,descriptionAnnonce,photoAnnonce,idClient,idCategorie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")){
                            Toast.makeText(AjoutAnnonce.this, "Annonce ajoutée Avec Succès", Toast.LENGTH_SHORT).show();
                            OpenListAnnonce();

                        }


                    }
                })

        );
    }

    private void OpenListAnnonce() {
        Intent intent = new Intent(this,ListeAnnonce.class);
        startActivity(intent);
    }


}