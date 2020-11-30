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

public class ForgotPassword extends AppCompatActivity {
    INodeJS resetMDP;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    EditText editMailR;
    Button ResetButton;

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
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_forgot_password);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        //Init Api
        Retrofit retrofit = RetrofitClient.getInstance();
        resetMDP= retrofit.create(INodeJS.class);
        //View
        ResetButton= (Button) findViewById(R.id.ResetButton);
        editMailR = (EditText) findViewById(R.id.editMailR);
        //Event
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass (editMailR.getText().toString());
            }
        });

    }

    private void resetPass(String email) {
        compositeDisposable.add(resetMDP.resetPass(email)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (s.contains("envoyé")) {
                    Toast.makeText(ForgotPassword.this, ""+s, Toast.LENGTH_SHORT).show();
                }
            }
        })
        );
    }

}
