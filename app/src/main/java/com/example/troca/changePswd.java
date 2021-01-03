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

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class changePswd extends AppCompatActivity {
    Button ValiderM;
    EditText code, NMdp, ConfirmMdpN;
    private INodeJS myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_change_pswd);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        code = findViewById(R.id.code);
        NMdp = findViewById(R.id.NMdp);
        ConfirmMdpN = findViewById(R.id.ConfirmMdpN);
        ValiderM = findViewById(R.id.ValiderM);


        ValiderM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pswd = code.getText().toString();
                String password = NMdp.getText().toString();
                String passwordC = ConfirmMdpN.getText().toString();
                if(password.equals(passwordC)){
                Retrofit retrofit = RetrofitClient.getInstance();
                myApi = retrofit.create(INodeJS.class);
                Call<Void> call = myApi.modifierMdp(pswd, password);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        new SweetAlertDialog(changePswd.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Succès")
                                .setContentText("Mot de passe changé avec succès")
                                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        openLogin();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                }
                else {
                    new SweetAlertDialog(changePswd.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Erreur")
                            .setContentText("Les mots de passes ne correspondent pas!")
                            .show();
                }
            }
        });

    }

    private void openLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}