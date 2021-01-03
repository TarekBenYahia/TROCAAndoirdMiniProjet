package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    EditText titreAnnonce, catAnnonce, photoAnnonce, descAnnonce, clientAnn;

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

        } catch (NullPointerException e) {
        }

        Spinner spinnerCat = findViewById(R.id.spinnerCat2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.categorie, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter1);
        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                final String text = parent.getItemAtPosition(position).toString();
                Retrofit retrofit = RetrofitClient.getInstance();
                myApi5 = retrofit.create(INodeJS.class);
                //view
                buttonAjoutAnnonce = (Button) findViewById(R.id.buttonAjoutAnnonce);
                titreAnnonce = (EditText) findViewById(R.id.titreAnnonce);
                photoAnnonce = (EditText) findViewById(R.id.photoAnnonce);

                descAnnonce = (EditText) findViewById(R.id.descAnnonce);
                clientAnn = (EditText) findViewById(R.id.clientAnn);

                //sharedPref
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String display = sharedPreferences.getString("display", "");

                try {
                    JSONObject p = new JSONObject(display);
                    clientAnn.setText(p.getString("NomPrenomClient"));
                    final String idClient = p.getString("idClient");
                    buttonAjoutAnnonce.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int idc = parseInt(idClient, 10);
                            String cat = "Plombier";

                            ajoutAnnonce(
                                    titreAnnonce.getText().toString(),
                                    descAnnonce.getText().toString(),
                                    photoAnnonce.getText().toString(),
                                    idc,
                                    text
                            );
                        }
                    }));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //EVENT
        //finish();

    }

    private void ajoutAnnonce(String titreAnnonce, String descriptionAnnonce, String photoAnnonce, int idClient, String idCategorie) {
        compositeDisposable.add(myApi5.ajoutAnnonce(titreAnnonce, descriptionAnnonce, photoAnnonce, idClient, idCategorie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("added")) {
                            new SweetAlertDialog(AjoutAnnonce.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(" Annonce ajoutée avec succès")
                                    .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            OpenListAnnonce();
                                        }
                                    }).show();
                        }
                    }
                })

        );
    }

    private void OpenListAnnonce() {
        Intent intent = new Intent(this, ListeAnnonce.class);
        startActivity(intent);
    }


}