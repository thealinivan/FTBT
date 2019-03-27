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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class AttractionDetailFragment extends Fragment implements View.OnClickListener {

    private TextView Reviews, reviewsTitle, _attrTitle, _attrLocation, _attrDescription;
    private Button btnAddReview, btnBook;
    private ScrollView scrollView;
    private ImageView _attrImg;
    private Attraction currentAttraction;

    public AttractionDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the fragment in a view
        View attrDetailLayout = inflater.inflate(R.layout.fragment_attraction_detail, container, false);
        currentAttraction = AttractionDetailActivity.getAttraction();
        //links
        Reviews = attrDetailLayout.findViewById(R.id.attr_detail_reviews);
        btnAddReview = attrDetailLayout.findViewById(R.id.attr_detail_new_review);
        scrollView = attrDetailLayout.findViewById(R.id.attr_details_scrollview);
        reviewsTitle = attrDetailLayout.findViewById(R.id.attr_detail_title_reviews);
        btnBook = attrDetailLayout.findViewById(R.id.attr_detail_book);

        _attrTitle = attrDetailLayout.findViewById(R.id.attr_detail_title);
        _attrLocation = attrDetailLayout.findViewById(R.id.attr_detail_location);
        _attrDescription = attrDetailLayout.findViewById(R.id.attr_detail_description);
        _attrImg = attrDetailLayout.findViewById(R.id.attr_detail_img);


        //populate data fields
        _attrTitle.setText(currentAttraction.getName());
        _attrLocation.setText(currentAttraction.getLocation());
        _attrDescription.setText(currentAttraction.getDescription());
        Picasso.get().load(currentAttraction.getImgUrl()).into(_attrImg);



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
