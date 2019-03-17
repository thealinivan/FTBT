package com.example.ftbt;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class AttractionDetailFragment extends Fragment implements View.OnClickListener {

    private TextView Reviews, reviewsTitle;
    private Button btnAddReview, btnBook;
    private ScrollView scrollView;

    public AttractionDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the fragment in a view
        View attrDetailLayout = inflater.inflate(R.layout.fragment_attraction_detail, container, false);

        //links
        Reviews = attrDetailLayout.findViewById(R.id.attr_detail_reviews);
        btnAddReview = attrDetailLayout.findViewById(R.id.attr_detail_new_review);
        btnBook = attrDetailLayout.findViewById(R.id.attr_detail_book);
        scrollView = attrDetailLayout.findViewById(R.id.attr_details_scrollview);
        reviewsTitle = attrDetailLayout.findViewById(R.id.attr_detail_title_reviews);

        //set on click listeners
        Reviews.setOnClickListener(this);
        btnAddReview.setOnClickListener(this);
        btnBook.setOnClickListener(this);

        return attrDetailLayout;
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.attr_detail_reviews:
                //smooth scroll to reviews section
                scrollView.smoothScrollTo(0, (int)reviewsTitle.getY());
            break;
            case R.id.attr_detail_new_review:
                //add review fragment to the parent fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.review_fragment_container, new ReviewFragment());
                ft.addToBackStack(null);
                ft.commit();

                break;
            case R.id.attr_detail_book:
                if(LoginActivity.token) {
                    //Handle website redirection
                    String url = "https://www.365tickets.co.uk/";
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
