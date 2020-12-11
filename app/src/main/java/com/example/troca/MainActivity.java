package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView textView3,textView2;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText editMail,editPswd;
    Button LoginButton;
    public String idC;

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
        //setContentView(R.layout.activity_main);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        textView3= (TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        textView2=(TextView) findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReset();
            }
        });
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);
        //view
        LoginButton = (Button) findViewById(R.id.LoginButton);
        editMail = (EditText) findViewById(R.id.editMail);
        editPswd = (EditText) findViewById(R.id.editPswd);
        // Event
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser (editMail.getText().toString(),editPswd.getText().toString());
            }
        });
    }



    private void openReset() {
        Intent intent=new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }

    private void loginUser(String email, String password) {

        compositeDisposable.add(myAPI.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("client"))
                        {
                            JSONObject  p= new JSONObject(s);
                            Toast.makeText(MainActivity.this, p.getString("NomPrenomClient"), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, "Login effectué avec succès"+s, Toast.LENGTH_SHORT).show();
                            //emptyFile();
                           // writeFile(s);
                            SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);

                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString("display",s);
                            editor.commit();
                            Log.d("mylog",s);


                            openChoix();

                            //readFile();
                        }
                        else if (s.contains("admin"))
                        {

                            openDashboard();
                        }
                        else if (s.contains("Pro"))
                        {
                            Toast.makeText(MainActivity.this, "Pro Connecté", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences= getSharedPreferences("ProData",MODE_PRIVATE);

                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString("display",s);
                            editor.commit();
                            Log.d("mylog",s);
                            openAcceuilPro();
                        }
                        else Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                    }
                })
        );
    }

    private void openAcceuilPro() {
        Intent intent = new Intent(this,AcceuilPro.class);
        startActivity(intent);
    }

    private void openDashboard() {
        Intent intent = new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }

    private void openChoix() {
        Intent intent = new Intent(this,ChoixType.class);
        startActivity(intent);
    }

    public void  openActivity2(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    public void writeFile(String d)
    {
        try {
            FileOutputStream fileOutputStream =openFileOutput("Data.txt",MODE_WORLD_WRITEABLE);
            fileOutputStream.write(d.getBytes());
            fileOutputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void emptyFile() throws FileNotFoundException {
       PrintWriter pw = new PrintWriter("Data.txt");
       pw.close();
    }

}