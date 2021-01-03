package com.example.troca.Commentaires;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.troca.R;

import java.util.List;

public class CommAdapter extends RecyclerView.Adapter<CommAdapter.MyViewHolder>  {
    private Context mcontext;
    private List<GetCommResponse> mcomm;
    public CommAdapter(Context mcontext,List<GetCommResponse>mcomm){
        this.mcontext=mcontext;
        this.mcomm=mcomm;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from((Context) mcontext);
        view = mInflater.inflate(R.layout.row_comment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCommResponse getCommResponse = mcomm.get(position);
        holder.contenu.setText(String.valueOf(getCommResponse.getContenu()));
    }

    @Override
    public int getItemCount() {
        return mcomm.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contenu,nbr_like;
        public MyViewHolder (View itemView){
            super(itemView);
            contenu=itemView.findViewById(R.id.comment_content);
            nbr_like=itemView.findViewById(R.id.nbr_like);
        }

    }
}
