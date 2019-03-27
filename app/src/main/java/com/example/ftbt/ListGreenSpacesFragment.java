package com.example.ftbt;


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

    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private AttractionAdapter adapter;
    private DatabaseReference dbRef;
    private ArrayList<Attraction> list = new ArrayList<>();
    private Query qRef;

    public ListGreenSpacesFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_green_spaces, container, false);
        rv = rootView.findViewById(R.id.green_spaces_recycler);
        manager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(manager);

        qRef = FirebaseDatabase.getInstance().getReference("Attractions")
                .getRef()
                .orderByChild("category")
                .equalTo("Green Spaces");
        qRef.addListenerForSingleValueEvent(listener);

        return rootView;
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onAttractionClick(int position) {

    }
}
