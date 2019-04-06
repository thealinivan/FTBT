package com.example.ftbt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListMainAttrFragment extends Fragment implements AttractionAdapter.Holder.AttractionClickListener {

    //object instantiation
    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private AttractionAdapter adapter;
    private ArrayList<Attraction> list = new ArrayList<>();
    private Query qRef;

    //empty constructor
    public ListMainAttrFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragments root view
        View rootView = inflater.inflate(R.layout.fragment_list_main_attr, container, false);
        //object linking with UI elements
        rv = rootView.findViewById(R.id.main_attr_recycler);
        manager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);

        //Firebase reference filtering attractions by Main category
        qRef = FirebaseDatabase.getInstance().getReference("Attractions")
                .getRef()
                .orderByChild("category")
                .equalTo("Main");
        qRef.addListenerForSingleValueEvent(listener);

        return rootView;
    }

    //listener which populates the attraction array called list
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //clear the arrray list and populate it in a loop
            list.clear();
            for (DataSnapshot dss:dataSnapshot.getChildren()){
                Attraction attr = dss.getValue(Attraction.class);
                list.add(attr);
            }

            //initiate adapter and attached it to recycler view
            adapter = new AttractionAdapter(list, ListMainAttrFragment.this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
    };

    //Overriden on click function implementation...
    //..of the AttractionClickListener interface from AttractionAdapter
    @Override
    public void onAttractionClick(int position) {
        //start AttractionDetailActivity passing the clicked list-position of the attraction object..
        // ..retrieved from the interface implementation in the Attraction Adapter
        Intent i = new Intent(getActivity(), AttractionDetailActivity.class);
        i.putExtra("Attraction", list.get(position));
        startActivity(i);
    }
}
