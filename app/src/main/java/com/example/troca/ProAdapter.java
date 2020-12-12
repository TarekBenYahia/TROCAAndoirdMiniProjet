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

import java.util.ArrayList;
import java.util.List;

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.MyViewHolder> {
    private Context mContext;
    private List<Professionnel> objects = new ArrayList<>();

    public ProAdapter(Context context,List<Professionnel> objects){
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mNom , mEmail , mNote , mLieu;
        private ImageView mRessource;
        private LinearLayout mContainer;
        public MyViewHolder (View view){
            super(view);
            mNom = view.findViewById(R.id.annonce_titre);
            mEmail = view.findViewById(R.id.annonce_description);
            mNote = view.findViewById(R.id.annonce_date);
            mRessource = view.findViewById(R.id.annonce_image);
            mContainer = view.findViewById(R.id.annonce_container);
            mLieu = view.findViewById(R.id.annonce_lieu);
        }
    }

    public ProAdapter() {
        super();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.annonce_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Professionnel object = objects.get(position);
        holder.mRessource.setImageResource(R.drawable.pro);
        holder.mNom.setText(object.getNomPrenomPro());
        holder.mEmail.setText(object.getEmailPro());
        holder.mLieu.setText(object.getAdressePro());
        String note = "30";
        holder.mNote.setText(note);
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,DetailedPro.class);
                intent.putExtra("idPro",object.getIdPro());
                intent.putExtra("titreAnnonce",object.getNomPrenomPro());
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
