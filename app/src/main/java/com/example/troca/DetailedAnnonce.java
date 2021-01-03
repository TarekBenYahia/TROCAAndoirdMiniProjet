package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import com.example.troca.RetroFit.RetrofitClient2;
import com.example.troca.Commentaires.AddCommentResponse;
import com.example.troca.Commentaires.AddCommnetRequest;
import com.example.troca.Commentaires.CommAdapter;
import com.example.troca.Commentaires.GetCommRequest;
import com.example.troca.Commentaires.GetCommResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class DetailedAnnonce extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre,mDescription,mDate;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private EditText comment_text,clientAnn,nomuser;
    private Button commentSubmit;
    private ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_detailed_annonce);
        try {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}




        mTitre = findViewById(R.id.post_detail_title);
        img=findViewById(R.id.post_detail_img);
        //comment_text=findViewById(R.id.comment_text);
        commentSubmit=findViewById(R.id.post_detail_add_comment_btn);
        mDescription=findViewById(R.id.post_detail_desc);
        mDate=findViewById(R.id.post_detail_date_name);
        Intent intent = getIntent();
        final int idannonce = parseInt(getIntent().getExtras().getString("idannonce"));
        String titre = getIntent().getExtras().getString("titre");
        String description=getIntent().getExtras().getString("description");
        String dateannonce = getIntent().getExtras().getString("date");
        String image = getIntent().getExtras().getString("image");


        mTitre.setText(titre);
        mDescription.setText(description);
        mDate.setText(dateannonce);
        comment_text = findViewById(R.id.post_detail_comment);

        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commnt=String.valueOf(comment_text.getText());
                AddCommnetRequest addCommnetRequest = new AddCommnetRequest();
                addCommnetRequest.setContenu(commnt);
                addCommnetRequest.setIdAnnonce(idannonce);
                addCommnetRequest.setIdClient(10);
                Call<AddCommentResponse> addCommentResponseCall = RetrofitClient2.getINodeJS().addComment(addCommnetRequest);
                addCommentResponseCall.enqueue(new Callback<AddCommentResponse>() {
                    @Override
                    public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                        AddCommentResponse addCommentResponse = response.body();
                        if(!response.isSuccessful()){
                            Toast.makeText(DetailedAnnonce.this,"please try again",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(DetailedAnnonce.this,"comment added",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<AddCommentResponse> call, Throwable t) {

                    }
                });
                GetCommRequest getCommRequest = new GetCommRequest();
                getCommRequest.setIdAnnonce(idannonce);
                Call<List<GetCommResponse>> call = RetrofitClient2.getINodeJS().getCommByAnnonce(getCommRequest);
                call.enqueue(new Callback<List<GetCommResponse>>() {
                    @Override
                    public void onResponse(Call<List<GetCommResponse>> call, Response<List<GetCommResponse>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(DetailedAnnonce.this, "No data available", Toast.LENGTH_LONG).show();
                            return;
                        }
                        RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_comment);
                        CommAdapter myAdapter = new CommAdapter(DetailedAnnonce.this, response.body());
                        myrv.setLayoutManager(new GridLayoutManager(DetailedAnnonce.this, 1));
                        myrv.setAdapter(myAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<GetCommResponse>> call, Throwable t) {
                        Toast.makeText(DetailedAnnonce.this, "Please check your network connexion", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


        GetCommRequest getCommRequest = new GetCommRequest();
        getCommRequest.setIdAnnonce(idannonce);
        Call<List<GetCommResponse>> call = RetrofitClient2.getINodeJS().getCommByAnnonce(getCommRequest);
        call.enqueue(new Callback<List<GetCommResponse>>() {
            @Override
            public void onResponse(Call<List<GetCommResponse>> call, Response<List<GetCommResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailedAnnonce.this, "No data available", Toast.LENGTH_LONG).show();
                    return;
                }
                RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_comment);
                CommAdapter myAdapter = new CommAdapter(DetailedAnnonce.this, response.body());
                myrv.setLayoutManager(new GridLayoutManager(DetailedAnnonce.this, 1));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<List<GetCommResponse>> call, Throwable t) {
                Toast.makeText(DetailedAnnonce.this, "Please check your network connexion", Toast.LENGTH_LONG).show();

            }
        });

        //sharedPref


        // Intent intent=getIntent();

    }

}