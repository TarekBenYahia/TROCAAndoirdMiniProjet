package com.example.troca.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.troca.CommandesFinies;
import com.example.troca.CommandesNonValidés;
import com.example.troca.PaymentActivity;
import com.example.troca.R;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommandesFiniesAdapter extends RecyclerView.Adapter<CommandesFiniesAdapter.MyViewHolder> {
    private Context mContext;
    private List<CommandesNonValidés> objects = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    INodeJS myAPI;
    public String mailPro = "tarek";

    public CommandesFiniesAdapter(Context context, List<CommandesNonValidés> objects) {
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEmail, mDate, mLieu;
        private RatingBar ratingBar;
        private Button noterB;

        private LinearLayout mContainer;

        public MyViewHolder(View view) {
            super(view);
            mEmail = view.findViewById(R.id.email_c);
            mDate = view.findViewById(R.id.date_c);
            mLieu = view.findViewById(R.id.lieu_c);
            ratingBar = view.findViewById(R.id.ratingBar);
            mContainer = view.findViewById(R.id.commande_finie_container);
            noterB = view.findViewById(R.id.noterB);
        }
    }

    public CommandesFiniesAdapter() {
        super();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.commande_finie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CommandesNonValidés object = objects.get(position);


        //holder.mEmail.setText(getEmail("8"));
        String idP = object.getIdPro();
        getEmail(idP, holder);
        holder.mDate.setText(object.getDate());
        holder.mLieu.setText(object.getLieu());
        holder.noterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String notation = String.valueOf(holder.ratingBar.getRating());
                new AlertDialog.Builder(mContext)
                        .setTitle("Note")
                        .setMessage("Voulez vous attribuer " + notation + " à ce professionnel ?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String idp = object.getIdPro();
                                Retrofit retrofit = RetrofitClient.getInstance();
                                myAPI = retrofit.create(INodeJS.class);
                                Call<Void> call = myAPI.noterPro(idp, notation);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Call<Void> call1 = myAPI.noteCommande(object.getIdCommande(), notation);
                                        call1.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                Toast.makeText(mContext, "Note Attribuée", Toast.LENGTH_SHORT).show();
                                                refresh();

                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                    }
                                });

                            }
                        })
                        .show();
            }

        });
        //  holder.ratingBar.setRating(Float.parseFloat(object.getNote()));
        String notation = object.getNote();
        if (notation != null) {
            holder.ratingBar.setRating(Float.parseFloat(notation));
            holder.noterB.setVisibility(View.INVISIBLE);
        } else
            holder.ratingBar.setRating(0);
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    private void getEmail(String idPro, final MyViewHolder holder) {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        compositeDisposable.add(myAPI.getEmailPro(idPro)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        JSONObject p = new JSONObject(s);
                        String mailc = p.getString("emailPro");
                        Log.d("email", mailc);

                        holder.mEmail.setText(mailc);
                    }
                }));
    }
    public void refresh(){
        refresh();
    }

}

