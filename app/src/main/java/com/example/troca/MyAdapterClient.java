package com.example.troca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterClient extends RecyclerView.Adapter<MyAdapterClient.MyViewHolder> {
    private Context mContext;
    private List<Client> objects = new ArrayList<>();

    public MyAdapterClient(Context context,List<Client> objects){
        this.objects = objects;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle , mDescription , mDate;
        private ImageView mRessource;
        private LinearLayout mContainer;
        public MyViewHolder (View view){
            super(view);
            mTitle = view.findViewById(R.id.name);
            mDescription = view.findViewById(R.id.email);
            mDate = view.findViewById(R.id.tel);
            mRessource = view.findViewById(R.id.image);
            mContainer = view.findViewById(R.id.container);
        }
    }

    public MyAdapterClient() {
        super();
    }

    @NonNull
    @Override
    public MyAdapterClient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.client_row,parent,false);
        return new MyAdapterClient.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterClient.MyViewHolder holder, int position) {
        final Client object = objects.get(position);
        holder.mRessource.setImageResource(R.drawable.client);
        holder.mTitle.setText(object.getNomPrenomClient());
        holder.mDescription.setText(object.getEmailClient());
        holder.mDate.setText(object.getTelClient());
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,detail_client.class);
                intent.putExtra("idClient",object.getIdClient());
                intent.putExtra("NomPrenomClient",object.getNomPrenomClient());
                intent.putExtra("emailClient",object.getEmailClient());
                intent.putExtra("telClient",object.getTelClient());
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
