package com.example.troca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommandesNClientAdapter extends RecyclerView.Adapter<CommandesNClientAdapter.MyViewHolder> {
    private Context mContext;
    private List<CommandesNonValidés> objects = new ArrayList<>();

    public CommandesNClientAdapter(Context context,List<CommandesNonValidés> objects){
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mEmail , mDate , mLieu;

        private LinearLayout mContainer;
        public MyViewHolder (View view){
            super(view);
            mEmail = view.findViewById(R.id.email_c);
            mDate = view.findViewById(R.id.date_c);
            mLieu = view.findViewById(R.id.lieu_c);
            mContainer = view.findViewById(R.id.commande_container);
        }
    }

    public CommandesNClientAdapter() {
        super();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.commande_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CommandesNonValidés object = objects.get(position);
        holder.mEmail.setText(object.getIdCommande());
        holder.mDate.setText(object.getDate());
        holder.mLieu.setText(object.getLieu());
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*
                Intent intent= new Intent(mContext,DetailedCommande.class);
                intent.putExtra("emailC",object.getIdClient());
                intent.putExtra("dateC",object.getDate());
                intent.putExtra("lieuC",object.getLieu());
                intent.putExtra("idC",object.getIdCommande());
                mContext.startActivity(intent);


             */
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cette commande est encore en attente");
                builder.setMessage("Voulez-vous?");
                builder.setPositiveButton("Attendre", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}


