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

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.Holder> {

    private ArrayList<Attraction> list;

    public AttractionAdapter(ArrayList<Attraction> list) {
        this.list = list;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;


        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attraction_item, viewGroup, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.tv.setText(list.get(i).getName());
        Picasso.get().load((list.get(i)).getImgUrl()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
