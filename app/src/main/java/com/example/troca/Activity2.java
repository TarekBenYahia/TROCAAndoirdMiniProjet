package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class Activity2 extends AppCompatActivity {

    INodeJS myApi2;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Button buttonRegister;
    EditText editName,editMailCl,editPswdCl,editTel,editCin,editNumCin,editDateN,editAdresse,editRole;

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
        this.setContentView(R.layout.activity_2);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi2 = retrofit.create(INodeJS.class);
        //view
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editName = (EditText) findViewById(R.id.editName);
        editMailCl = (EditText) findViewById(R.id.editMailCl);
        editPswdCl = (EditText) findViewById(R.id.editPswdCl);
        editTel = (EditText) findViewById(R.id.editTel);
        editCin = (EditText) findViewById(R.id.editCin);
        editNumCin = (EditText) findViewById(R.id.editNumCin);
        editDateN = (EditText) findViewById(R.id.editDateN);
        editAdresse = (EditText) findViewById(R.id.editAdresse);
        editRole = (EditText) findViewById(R.id.editRole);

        //EVENT
        buttonRegister.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tel=parseInt(editTel.getText().toString(),10);
                int numeroCin = parseInt( editNumCin.getText().toString(),10);
                registerUser(
                        editName.getText().toString(),
                        editMailCl.getText().toString(),
                        editPswdCl.getText().toString(),
                        tel,
                        editDateN.getText().toString(),
                        numeroCin,
                        editCin.getText().toString(),
                        editAdresse.getText().toString(),
                        editRole.getText().toString()
                );
            }
        }));
    }
    private void registerUser(String name, String email, String password, int tel, String dateN, int numCin, String cin, String adresse, String role) {
        compositeDisposable.add(myApi2.registerUser(name,email,password,tel,dateN,numCin,cin,adresse,role)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")){
                            Toast.makeText(Activity2.this, "Inscrit Avec Succès", Toast.LENGTH_SHORT).show();
                            OpenLogin();

                        }
                        else Toast.makeText(Activity2.this, ""+s, Toast.LENGTH_SHORT).show();

                    }
                })

        );
    }

    private void OpenLogin() {

    }

}