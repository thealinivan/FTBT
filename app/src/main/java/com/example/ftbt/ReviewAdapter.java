package com.example.ftbt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder>{
    private ArrayList<Review> list;


    public ReviewAdapter(ArrayList<Review> list) {
        this.list = list;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView name, email, description;


        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ri_fName);
            email = itemView.findViewById(R.id.ri_email);
            description = itemView.findViewById(R.id.ri_description);
        }

    }
    @NonNull
    @Override
    public ReviewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        ReviewAdapter.Holder holder = new ReviewAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.Holder holder, int i) {

        //content owner's name
        String name = list.get(i).getUserID().split("\\@")[0];
        String _name = name.substring(0, 1).toUpperCase() + name.substring(1);
        holder.name.setText(_name);

        //content owner's email
        holder.email.setText(list.get(i).getUserID());

        //review text
        holder.description.setText(list.get(i).getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
