package com.example.ftbt;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.manager.RequestManagerRetriever;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChangePassword extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private TextView oldPass, newPass, newPassConf;
    private Button cpSubmitBtn;
    private ProgressBar cpPb;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set up button on app bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        oldPass = findViewById(R.id.cp_old_password);
        newPass = findViewById(R.id.cp_new_password);
        newPassConf = findViewById(R.id.cp_conf_new_password);
        cpSubmitBtn = findViewById(R.id.cp_submitBtn);
        cpPb = findViewById(R.id.cp_progress_bar);
        cpPb.setVisibility(View.INVISIBLE);

        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        cpSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPass == null && newPass == null && newPassConf == null )
                {
                    Toast.makeText(ChangePassword.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                } else {

                    final String _oldPass = oldPass.getText().toString().trim();
                    final String _newPass = newPass.getText().toString().trim();
                    final String _newPassConf = newPass.getText().toString().trim();

                    if (!LoginActivity.getCurrentUser().getPassword().equals(_oldPass))
                    {
                        Toast.makeText(ChangePassword.this, "Old password don't match!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!_newPass.equals(_newPassConf))
                    {
                        Toast.makeText(ChangePassword.this, "New passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        cpPb.setVisibility(View.VISIBLE);
                        cpSubmitBtn.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                User user = LoginActivity.getCurrentUser();
                                user.setPassword(_newPass);
                                //store the user in firebase
                                dbRef.child((user.getEmail()).replace(".", ",")).setValue(user);
                                Toast.makeText(ChangePassword.this, "Account password has been changed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangePassword.this, AccountActivity.class));
                            }
                        }, 1000);

                    }
                }


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



}
