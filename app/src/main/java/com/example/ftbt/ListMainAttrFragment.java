package com.example.ftbt;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListMainAttrFragment extends Fragment {


    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private AttractionAdapter adapter;
    private DatabaseReference dbRef;
    private ArrayList<Attraction> list = new ArrayList<>();


    public ListMainAttrFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View MainAttrLayout = inflater.inflate(R.layout.fragment_list_main_attr, container, false);
            rv = MainAttrLayout.findViewById(R.id.main_attr_list);
            manager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(manager);

        dbRef = FirebaseDatabase.getInstance().getReference("Attractions");
        dbRef.addListenerForSingleValueEvent(listener);


        return MainAttrLayout;
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            for (DataSnapshot dss:dataSnapshot.getChildren()){
                Attraction attr = dss.getValue(Attraction.class);
                list.add(attr);
            }
            adapter = new AttractionAdapter(list);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
