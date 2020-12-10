package com.example.troca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.StadeAdapterVH> {

    private List<Comment> stadiumslist;
    private Context context;
    private ClickedItem clickedItem;
    private EditText comment_text;
    private Button commentSubmit;
    public CommentaireAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<Comment> stadiumslist) {

        this.stadiumslist = stadiumslist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StadeAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CommentaireAdapter.StadeAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_commentaire, parent,false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull StadeAdapterVH holder, int position) {

        final Comment stades = stadiumslist.get(position);

        String nomstade = stades.getContenu();
        //int prix = stades.getPrix();

        holder.nomstade.setText(nomstade);
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedStade(stades);
            }
        });
    }

    public interface ClickedItem{
        public void ClickedStade(Comment stades);
    }

    @Override
    public int getItemCount() {
        return stadiumslist.size();
    }

    public class StadeAdapterVH extends RecyclerView.ViewHolder {

        TextView nomstade;
        Button imageMore;

        public StadeAdapterVH(@NonNull View itemView) {
            super(itemView);
            nomstade = itemView.findViewById(R.id.nomstade);
            imageMore = itemView.findViewById(R.id.imageMore);
        }
    }

}