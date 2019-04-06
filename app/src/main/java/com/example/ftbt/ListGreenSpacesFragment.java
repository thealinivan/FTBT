package com.example.ftbt;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListGreenSpacesFragment extends Fragment implements AttractionAdapter.Holder.AttractionClickListener {

    //object instantiation
    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private AttractionAdapter adapter;
    private DatabaseReference dbRef;
    private ArrayList<Attraction> list = new ArrayList<>();
    private Query qRef;

    //empty constructor
    public ListGreenSpacesFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragments root view
        View rootView = inflater.inflate(R.layout.fragment_list_green_spaces, container, false);

        //object linking with UI elements
        rv = rootView.findViewById(R.id.green_spaces_recycler);
        manager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(manager);

        //Firebase reference filtering attractions by green spaces category
        qRef = FirebaseDatabase.getInstance().getReference("Attractions")
                .getRef()
                .orderByChild("category")
                .equalTo("Green Spaces");

        //attach the listener wich populated the attraction array called list
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
            adapter = new AttractionAdapter(list, ListGreenSpacesFragment.this);
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
