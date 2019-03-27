package com.example.ftbt;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.Holder> {

    private ArrayList<Attraction> list;


    public AttractionAdapter(ArrayList<Attraction> list) {
        this.list = list;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv, tv2, tv3;


        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);

            //onclick listener to start detail activity
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
 ///try to pass attr obj to details activity
                    //Attraction attr = list.get(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), AttractionDetailActivity.class);
                    //intent.putExtra("attrListPos", attr);
                    v.getContext().startActivity(intent);

                }
            });




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

        String user = list.get(i).getUserID().split("\\@")[0];
        String _user = user.substring(0, 1).toUpperCase() + user.substring(1);
        holder.tv2.setText(_user);

        holder.tv3.setText(list.get(i).getLocation().split("\\@")[0]);

        Picasso.get().load((list.get(i)).getImgUrl()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
