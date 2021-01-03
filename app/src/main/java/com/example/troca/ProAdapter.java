package com.example.troca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.MyViewHolder> {
    private Context mContext;
    private List<Professionnel> objects = new ArrayList<>();

    public ProAdapter(Context context,List<Professionnel> objects){
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mNom , mEmail , mNote , mLieu,note;
        private ImageView mRessource;
        private LinearLayout mContainer;
        public MyViewHolder (View view){
            super(view);
            mNom = view.findViewById(R.id.pro_titre);
            mEmail = view.findViewById(R.id.pro_description);
            mNote = view.findViewById(R.id.pro_date);
            mRessource = view.findViewById(R.id.pro_image);
            mContainer = view.findViewById(R.id.pro_container);
            note = view.findViewById(R.id.note_proo);
            mLieu = view.findViewById(R.id.pro_lieu);
        }
    }

    public ProAdapter() {
        super();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pro_liste_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Professionnel object = objects.get(position);
        Retrofit retrofit = RetrofitClient.getInstance();
        String url = String.valueOf(retrofit.baseUrl());
        Glide.with(mContext)
                .load(url+"cl/uploads/"+object.getCinPro())
                .override(400,400)
                .into(holder.mRessource);
       // holder.mRessource.setImageResource(R.drawable.pro);
        holder.mNom.setText(object.getIdCategorie());
        holder.mEmail.setText(object.getEmailPro());
        holder.mLieu.setText(object.getAdressePro());
        holder.note.setText(object.getNotePro());
        String note = "30";
        holder.mNote.setText(note);
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,DetailedPro.class);
                intent.putExtra("idPro",object.getIdPro());
                intent.putExtra("titreAnnonce",object.getIdCategorie());
                intent.putExtra("descriptionAnnonce",object.getEmailPro());
                intent.putExtra("dateAnnonce",object.getNotePro());
                intent.putExtra("lieu",object.getAdressePro());
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
