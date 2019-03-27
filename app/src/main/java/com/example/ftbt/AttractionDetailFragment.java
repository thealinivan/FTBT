package com.example.ftbt;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AttractionDetailFragment extends Fragment implements View.OnClickListener {

    private TextView reviews, reviewsTitle, _attrTitle, _attrLocation, _attrDescription;
    private Button btnAddReview, btnBook;
    private ScrollView scrollView;
    private ImageView _attrImg;
    private Attraction currentAttraction;

    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;
    private ReviewAdapter adapter;
    private ArrayList<Review> reviewsList = new ArrayList<>();
    private Query qRef;

    public AttractionDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the fragment in a view
        View attrDetailLayout = inflater.inflate(R.layout.fragment_attraction_detail, container, false);
        currentAttraction = AttractionDetailActivity.getCurrentAttraction();

        //click action elements
        reviews = attrDetailLayout.findViewById(R.id.attr_detail_reviews);
        btnAddReview = attrDetailLayout.findViewById(R.id.attr_detail_new_review);
        scrollView = attrDetailLayout.findViewById(R.id.attr_details_scrollview);
        reviewsTitle = attrDetailLayout.findViewById(R.id.attr_detail_title_reviews);
        btnBook = attrDetailLayout.findViewById(R.id.attr_detail_book);

        //attraction details content
        _attrTitle = attrDetailLayout.findViewById(R.id.attr_detail_title);
        _attrLocation = attrDetailLayout.findViewById(R.id.attr_detail_location);
        _attrDescription = attrDetailLayout.findViewById(R.id.attr_detail_description);
        _attrImg = attrDetailLayout.findViewById(R.id.attr_detail_img);


        //populate data fields
        _attrTitle.setText(currentAttraction.getName());
        _attrLocation.setText(currentAttraction.getLocation());
        _attrDescription.setText(currentAttraction.getDescription());
        Picasso.get().load(currentAttraction.getImgUrl()).into(_attrImg);

        //reviews RecyclerView elements
        rv = attrDetailLayout.findViewById(R.id.reviews_recycler);
        manager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);

        //get all currentAttraction reviews
        qRef = FirebaseDatabase.getInstance().getReference("Reviews")
                .getRef()
                .orderByChild("attractionID")
                .equalTo(AttractionDetailActivity.getCurrentAttraction().getName());
        qRef.addListenerForSingleValueEvent(listener);


        //set on click listeners
        reviews.setOnClickListener(this);
        btnAddReview.setOnClickListener(this);
        btnBook.setOnClickListener(this);

        return attrDetailLayout;
    }


    ////Failed attemtp to get new review instant display
    //Currently you need to restart the details activity to see it
    @Override
    public void onStart() {
        super.onStart();

        //initiate adapter and attached it to recycler view for instant review display
        adapter = new ReviewAdapter(reviewsList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //smooth scroll to reviews section to see the review wich has been just added
        scrollView.smoothScrollTo(0, (int)reviewsTitle.getY());
    }

    ////Failed attempt to get New review posted live
    //Currently you need to restart the details activity to see it
    @Override
    public void onResume() {
        super.onResume();
        //initiate adapter and attached it to recycler view for instant review display
        adapter = new ReviewAdapter(reviewsList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //smooth scroll to reviews section to see the review wich has been just added
        scrollView.smoothScrollTo(0, (int)reviewsTitle.getY());

    }


    //listener to populate reviewlist Array with data
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            reviewsList.clear();
            for (DataSnapshot dss:dataSnapshot.getChildren()){
                Review review = dss.getValue(Review.class);
                reviewsList.add(review);
            }

            //initiate adapter and attached it to recycler view
            adapter = new ReviewAdapter(reviewsList);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.attr_detail_reviews:
                //smooth scroll to reviews section
                scrollView.smoothScrollTo(0, (int)reviewsTitle.getY());
            break;
            case R.id.attr_detail_new_review:
                if(!(LoginActivity.token)) {
                    Intent iLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(iLogin);
                    Toast.makeText(getActivity(), "You need to login first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //add review fragment to the parent fragment
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.review_fragment_container, new ReviewFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
                break;
            case R.id.attr_detail_book:
                if(LoginActivity.token) {
                    //Handle website redirection
                    String url = currentAttraction.getLinkUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else{
                    Intent iLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(iLogin);
                    Toast.makeText(getActivity(), "You need to login first!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }

    public static AttractionDetailFragment newInstance() {
        return new AttractionDetailFragment();
    }
}
