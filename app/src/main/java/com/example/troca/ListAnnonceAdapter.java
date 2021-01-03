package com.example.troca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.parseInt;

public class ListAnnonceAdapter extends RecyclerView.Adapter<ListAnnonceAdapter.MyViewHolder>{
    private Context mContext ;
    private List<Annonce> annonceList ;

    public ListAnnonceAdapter(Context mContext, List<Annonce> annonceList) {
        this.mContext = mContext;
        this.annonceList = annonceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from((Context) mContext);
        view = mInflater.inflate(R.layout.annonce_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAnnonceAdapter.MyViewHolder holder, int position) {
        final Annonce annonce = annonceList.get(position);
        holder.titre.setText(String.valueOf(annonce.getTitreAnnonce()));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailedAnnonce.class);
                intent.putExtra("idannonce",String.valueOf(annonce.getIdAnnonce()));
                intent.putExtra("titre",annonce.getTitreAnnonce());
                intent.putExtra("description",annonce.getDescriptionAnnonce());
                intent.putExtra("date",annonce.getDateAnnonce());
               intent.putExtra("image",annonce.getRessourceImg());
                mContext.startActivity(intent);
            }
        });
        holder.description.setText(String.valueOf(annonce.getDescriptionAnnonce()));
        holder.date.setText(String.valueOf(annonce.getDateAnnonce()));
        Picasso.get().load(annonce.getRessourceImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return annonceList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre,description,date , username;
        ImageView img ;
        CardView cardView;

        public MyViewHolder( View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.annonce_titre);
            description = (TextView) itemView.findViewById(R.id.annonce_description);
            date=(TextView) itemView.findViewById(R.id.annonce_date);

            img=(ImageView) itemView.findViewById(R.id.annonce_image);
            cardView=(CardView) itemView.findViewById(R.id.annonce_card);
        }}
}
