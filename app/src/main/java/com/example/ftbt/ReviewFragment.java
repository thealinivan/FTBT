package com.example.ftbt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class ReviewFragment extends Fragment implements View.OnClickListener {

    private Button cancelReview, submitReview;


    public ReviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the fragment in a view
        View reviewLayout = inflater.inflate(R.layout.fragment_review, container, false);

        //links
        submitReview = reviewLayout.findViewById(R.id.cancel_review_button);
        cancelReview = reviewLayout.findViewById(R.id.submit_review_button);

        //on click listeners
        submitReview.setOnClickListener(this);
        cancelReview.setOnClickListener(this);

        return reviewLayout;
    }

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onClick(View v) {
        //pre-requisites to manipulate the fragment
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();


        switch(v.getId()){
            case R.id.submit_review_button:
                if(!(LoginActivity.token)) {
                    Intent iLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(iLogin);
                    Toast.makeText(getActivity(), "You need to login first!", Toast.LENGTH_SHORT).show();

                }
                else {
                    //Handle submision of the review into firebase
                    //.....
                    //Remove the fragment as no longer needed
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }
                }
                break;
            case R.id.cancel_review_button:
                //Remove the fragment as no longer needed
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                if(fm1.getBackStackEntryCount()>0) {
                    fm1.popBackStack();
                }

                break;
            default:
        }
    }
}
