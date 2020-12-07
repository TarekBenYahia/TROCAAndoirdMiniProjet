package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DetailedPro extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private Button AjoutC;
    INodeJS myApi3;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detailed_pro);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        AjoutC= (Button) findViewById(R.id.AjoutC);

        mTitre= findViewById(R.id.titre);
        mDescription= findViewById(R.id.description);
        mDate= findViewById(R.id.date);
        mImage= findViewById(R.id.imageA);

        Intent intent=getIntent();
        String title = intent.getStringExtra("titreAnnonce");
        String desc= intent.getStringExtra("descriptionAnnonce");
        String date = intent.getStringExtra("dateAnnonce");
        if (intent!= null){
            mTitre.setText(title);
            mDescription.setText(desc);
            mDate.setText(date);
            Glide.with(DetailedPro.this).load(R.drawable.pro).into(mImage);
        }
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi3 = retrofit.create(INodeJS.class);
        AjoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
                String display = sharedPreferences.getString("display","");

                try {
                    JSONObject p= new JSONObject(display);
                  String idC=  p.getString("idClient");
                    Date currentTime = Calendar.getInstance().getTime();
                    ajouterCommande(idC,"7","tunis", "2019-01-01",30);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void ajouterCommande(String id,String id2,String lieu, String date, int prix) {
        compositeDisposable.add(myApi3.ajouterCommande(id,id2,lieu,date,prix)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")){
                            Toast.makeText(DetailedPro.this, "Commande ajoutée Avec Succès", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(DetailedPro.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

}