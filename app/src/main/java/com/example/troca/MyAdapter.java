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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<Annonce> objects = new ArrayList<>();

    public MyAdapter(Context context,List<Annonce> objects){
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle , mDescription , mDate;
        private ImageView mRessource;
        private LinearLayout mContainer;
        public MyViewHolder (View view){
            super(view);
            mTitle = view.findViewById(R.id.annonce_titre);
            mDescription = view.findViewById(R.id.annonce_description);
            mDate = view.findViewById(R.id.annonce_date);
            mRessource = view.findViewById(R.id.annonce_image);
            mContainer = view.findViewById(R.id.annonce_container);
        }
    }

    public MyAdapter() {
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
        final Annonce object = objects.get(position);
        holder.mRessource.setImageResource(R.drawable.client);
        holder.mTitle.setText(object.getTitreAnnonce());
        holder.mDescription.setText(object.getDescriptionAnnonce());
        holder.mDate.setText(object.getDateAnnonce());
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,DetailedAnnonce.class);
                intent.putExtra("titreAnnonce",object.getTitreAnnonce());
                intent.putExtra("descriptionAnnonce",object.getDescriptionAnnonce());
                intent.putExtra("dateAnnonce",object.getDateAnnonce());
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
