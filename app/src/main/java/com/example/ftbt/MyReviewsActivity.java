package com.example.ftbt;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyReviewsActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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



}
