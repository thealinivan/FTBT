package com.example.ftbt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private Button logoutBtn, myAttractionsBtn, myReviewsBtn, changePasswordBtn;
    private TextView accountSubtitle, accountName, accountEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up button on app bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        logoutBtn = findViewById(R.id.account_sign_out);
        myAttractionsBtn = findViewById(R.id.account_my_attractions_button);
        myReviewsBtn = findViewById(R.id.account_my_reviews_button);
        accountSubtitle = findViewById(R.id.account_subtitle);
        accountName = findViewById(R.id.account_name_content);
        accountEmail = findViewById(R.id.account_email_content);
        changePasswordBtn = findViewById(R.id.account_password);


        if (LoginActivity.token = true) {
            User user = LoginActivity.getCurrentUser();
            accountSubtitle.setText(capitalize(user.getfName()) + " " + capitalize(user.getlName()));
            accountName.setText(capitalize(user.getfName()) + " " + capitalize(user.getlName()));
            accountEmail.setText(user.getEmail());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.token = false;
                Toast.makeText(AccountActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                Intent iLogin = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(iLogin);
            }
        });

        myAttractionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMyAttr = new Intent(AccountActivity.this, MyAttractionsActivity.class);
                startActivity(iMyAttr);
            }
        });

        myReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, MyReviewsActivity.class));
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPasChang = new Intent(AccountActivity.this, ChangePassword.class);
                startActivity(iPasChang);
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

    public String capitalize(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }



}
