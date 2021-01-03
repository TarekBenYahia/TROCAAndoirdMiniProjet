package com.example.troca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class ChoixType extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private  TextView nomClient , textC;
    private ImageView trocImg,proImg,botC,bulC;
    Animation BottomAnim;
    //String username = getIntent().getStringExtra("USERNAME");



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_choix_type);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        BottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);



        trocImg=(ImageView) findViewById(R.id.trocImg);
        textC=findViewById(R.id.textC);
        bulC= findViewById(R.id.bulC);
        proImg=(ImageView) findViewById(R.id.proImg);
        botC=(ImageView) findViewById(R.id.botC);
        trocImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListeAnnonce();
            }
        });
        botC.setAnimation(BottomAnim);
        textC.setAnimation(BottomAnim);
        bulC.setAnimation(BottomAnim);

        botC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChatBot();
            }
        });

        proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListePro();
            }
        });

        nomClient = (TextView) findViewById(R.id.nomClient);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerP);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.profil,R.layout.spiner_text_style);
        adapter.setDropDownViewResource(R.layout.spiner_text_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");

        try {
            JSONObject  p= new JSONObject(display);
            nomClient.setText( p.getString("NomPrenomClient"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // readFile();
        //System.out.println(username);
        nomClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProfile();
            }
        });




    }

    private void openChatBot() {
        Intent intent = new Intent(this,ChatActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    private void viewProfile() {
        Intent intent=new Intent(this,ProfilClient.class);
        startActivity(intent);
    }
    private void openListePro(){
        Intent intent = new Intent(this, ListePro.class);
        startActivity(intent);
    }

    private void openListeAnnonce() {
        Intent intent= new Intent(this,ListeAnnonce.class);
        startActivity(intent);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.contains("Mes Commandes en attente")){
            openCommandesEnAttentes();
        }
        else if(text.contains("Mes Commandes validées")){
            openCommandesValides();
        }
        else if(text.contains("Historique de mes commandes")){
            openCommandesFinies();
        }
        else if(text.contains("Déconnexion")){
            SharedPreferences preferences =getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
//            ProgressDialog dialog = ProgressDialog.show(this,"","Loading...",true);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        }


    }

    private void openCommandesFinies() {
        Intent intent = new Intent(this,CommandesFinies.class);
        startActivity(intent);
    }

    private void openCommandesValides() {
        Intent intent = new Intent(this,CommandesValidesClient.class);
        startActivity(intent);
    }

    private void openCommandesEnAttentes() {
        Intent intent = new Intent(this, CommandesEnAttentesCl.class);
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}