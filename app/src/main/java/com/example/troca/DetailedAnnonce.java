package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DetailedAnnonce extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;
    private EditText contenuC;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button sendCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detailed_annonce);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mTitre= findViewById(R.id.titre);
        mDescription= findViewById(R.id.description);
        mDate= findViewById(R.id.date);
        mImage= findViewById(R.id.imageA);
        contenuC= (EditText) findViewById(R.id.contenuC);




        sendCom= (Button) findViewById(R.id.sendCom);
        sendCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
                String display = sharedPreferences.getString("display","");

                try {
                    JSONObject  p= new JSONObject(display);
                    String idClient= p.getString("idClient");
                   // String contenu = contenuC.getText().toString();
                    ajouterCommentaire(idClient,contenuC.getText().toString(),"7","0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);



        Intent intent=getIntent();

        String title = intent.getStringExtra("titreAnnonce");
        String desc= intent.getStringExtra("descriptionAnnonce");
        String date = intent.getStringExtra("dateAnnonce");
        if (intent!= null){
            mTitre.setText(title);
            mDescription.setText(desc);
            mDate.setText(date);
            Glide.with(DetailedAnnonce.this).load(R.drawable.client).into(mImage);
        }
    }

    private void ajouterCommentaire(String salut, String s, String s1, String s2) {
        compositeDisposable.add(myAPI.ajouterCommentaire(salut, s, s1, s2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("successfully")){
                            Toast.makeText(DetailedAnnonce.this, "commentaire ajouté avec succès", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
        );

    }
}