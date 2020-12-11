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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class DetailedAnnonce extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private EditText comment_text,clientAnn;
    private Button commentSubmit;



    INodeJS myApi8;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this. setContentView(R.layout.activity_detailed_annonce);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Retrofit retrofit = RetrofitClient.getInstance();

        myApi8= retrofit.create(INodeJS.class);

        mTitre= findViewById(R.id.titre);
        mDescription= findViewById(R.id.description);
        mDate= findViewById(R.id.date);
        mImage= findViewById(R.id.imageA);
        comment_text=findViewById(R.id.comment_text);
        commentSubmit = (Button) findViewById(R.id.commentSubmit);
        clientAnn = (EditText) findViewById(R.id.clientAnn);

        String title = "ahla";
        String desc= "ahla";
        String date = "ahla";
        // if (intent!= null){
        mTitre.setText(title);
        mDescription.setText(desc);
        mDate.setText(date);
        Glide.with(DetailedAnnonce.this).load(R.drawable.client).into(mImage);

        //sharedPref
        SharedPreferences sharedPreferences= getSharedPreferences("UserData",MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");

        try {
            JSONObject p= new JSONObject(display);
            clientAnn.setText( p.getString("NomPrenomClient"));
            final String idClient = p.getString("idClient");

            commentSubmit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int idc=parseInt(idClient,10);


                ajoutCommentaire(
                        comment_text.getText().toString(),
               idc,
                        3
                );
                Toast.makeText(DetailedAnnonce.this, "Annonce Ajoutée Avec succès", Toast.LENGTH_SHORT).show();
            }
        }));
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void ajoutCommentaire(String Contenu,int idClient,int idAnnonce)
    {
        compositeDisposable.add(myApi8.ajoutCommentaire(Contenu,idClient,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")){
                            Toast.makeText(DetailedAnnonce.this, "Annonce ajoutée Avec Succès", Toast.LENGTH_SHORT).show();
                         //   OpenListAnnonce();

                        }


                    }
                })

        );
    }

    // Intent intent=getIntent();

      //  }

}