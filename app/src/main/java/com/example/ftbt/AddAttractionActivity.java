package com.example.ftbt;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

public class AddAttractionActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private ImageView pick;
    public static final int REQUESTCODE = 100;
    private Uri uri;
    private StorageReference sRef;
    private Button submitBtn;
    private EditText attrName, attrDescription;
    private ProgressBar progressBar;
    private TextView attrLocation;
    private String imgUrl;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attraction);

        //set toolbar as app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up button on app bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //links
        pick = findViewById(R.id.add_attr_img);
        attrName = findViewById(R.id.add_attr_name);
        attrDescription = findViewById(R.id.add_attr_description);
        attrLocation = findViewById(R.id.add_attr_location);
        submitBtn = findViewById(R.id.add_attr_add_btn);
        progressBar = findViewById(R.id.add_attr_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        submitBtn.setVisibility(View.VISIBLE);

        sRef = FirebaseStorage.getInstance().getReference("Images");
        dbRef = FirebaseDatabase.getInstance().getReference("Attractions");

        //on click listeners
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUESTCODE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        attrLocation.setText(attrLocation());
                    }
                }, 1000);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user feedback if missing info
                if (TextUtils.isEmpty(attrName.getText().toString())) {
                    Toast.makeText(AddAttractionActivity.this, "Enter Attraction Name!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(attrDescription.getText().toString())) {
                    Toast.makeText(AddAttractionActivity.this, "Enter Attraction Description!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.INVISIBLE);

                final StorageReference reference = sRef.child(attrName.getText().toString()+"."+getExtension(uri));


                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imgUrl = uri.toString();
                                        Log.d("TAG", imgUrl);

                                        Attraction attr = new Attraction(attrName.getText().toString(),
                                                attrDescription.getText().toString(),
                                                attrLocation(),
                                                attrCategory(),
                                                imgUrl,
                                                linkUrl(),
                                                (LoginActivity.getCurrentUser()).getEmail());

                                        //dbRef.child(dbRef.push().getKey()).setValue(attr);
                                        dbRef.child(attr.getName()).setValue(attr);
                                    }
                                });


                                Toast.makeText(AddAttractionActivity.this, "New attraction added!", Toast.LENGTH_SHORT).show();
                                Intent iHome = new Intent(AddAttractionActivity.this, HomeActivity.class);
                                startActivity(iHome);

                            }
                        }, 1000);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        submitBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(AddAttractionActivity.this, "Something went wrong! Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    //right menu along with action icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //right menu
        getMenuInflater().inflate(R.menu.menu_right, menu);

        //right menu share action locator
        MenuItem menuitem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuitem);
        setShareActionIntent("Check this place in London!");
        return super.onCreateOptionsMenu(menu);
    }

    //Handle share action
    private void setShareActionIntent(String text){
        Intent i = new Intent (Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(i.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(i);
    }

    //Handle selection of action on right menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //resolve the clash by adding break after each case
            case R.id.action_create_attraction:
                if(LoginActivity.token) {
                    Intent iAddAttr = new Intent(this, AddAttractionActivity.class);
                    startActivity(iAddAttr);
                }
                else{
                    Intent iLogin = new Intent(this, LoginActivity.class);
                    startActivity(iLogin);
                    Toast.makeText(this, "You need to login first!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_account:

                if(LoginActivity.token) {
                    Intent iAccount = new Intent(this, AccountActivity.class);
                    startActivity(iAccount);
                }
                else{
                    Intent iLogin = new Intent(this, LoginActivity.class);
                    startActivity(iLogin);
                }
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    //return the image extension(.jpg or .png)
    private String getExtension(Uri uri)
    {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton().getSingleton();
        return map.getExtensionFromMimeType((resolver.getType(uri)));
    }

    //test result and set up the image in the registration activity user image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            //the user has successfully picked an image

            //saving its reference in Uri variable
            uri = data.getData();

            //set up the image in imageview
            Picasso.get().load(uri).into(pick);

        }
    }


    //Check for duplicate ID (attraction title) in the database
    public boolean isDuplicateID(String attractionTitle){
        boolean isDupl = true;
        //Check firebase for duplicate ID here
        //...
        return isDupl;
    }

    public String attrLocation() {
        String address = "London";

        //Get Lat and Lng
        Location loc = new Location("");
        Double lng = loc.getLongitude();
        Double lat = loc.getLatitude();

        //Get address based on Lat and Long

            return address ;
    }

    //check if edit text fields are empty
    private boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }

    public String attrCategory(){ return "Main"; }
    public String linkUrl(){ return "https://www.365tickets.co.uk/"; }


}
