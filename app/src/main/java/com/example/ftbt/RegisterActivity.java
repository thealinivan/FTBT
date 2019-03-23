package com.example.ftbt;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private EditText email, pass, passConf, fName, lName;
    private Button btnLogin, btnRegister, btnGuest;
    private ProgressBar progressBar;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up button on app bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.register_email);
        pass = findViewById(R.id.register_password);
        passConf = findViewById(R.id.register_passwordConfirm);
        fName = findViewById(R.id.register_fName);
        lName = findViewById(R.id.register_lName);

        btnLogin = findViewById(R.id.register_loginBtn);
        btnRegister = findViewById(R.id.register_registerBtn);
        btnGuest = findViewById(R.id.register_guestBtn);

        progressBar = findViewById(R.id.register_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);

        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle user registration in firebase

                    //user feedback if missing info
                    if(TextUtils.isEmpty(fName.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Enter First Name!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(TextUtils.isEmpty(lName.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Enter Last Name!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(TextUtils.isEmpty(email.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(TextUtils.isEmpty(pass.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(TextUtils.isEmpty(passConf.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Confirm Password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //user feedback if password do not match
                    else if(!(pass.getText().toString().equals(passConf.getText().toString()))) {
                        pass.setText("");
                        passConf.setText("");
                        Toast.makeText(RegisterActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(isDuplicateID(email.getText().toString())){
                        //email.setText("");
                        Toast.makeText(RegisterActivity.this, "Email already assigned to existing account!", Toast.LENGTH_SHORT).show();
                    }
                    else if(!(isEmailValid(email.getText().toString()))){
                        //attrTitle.setText("");
                        Toast.makeText(RegisterActivity.this, "Wrong email!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //display progress bar
                        btnRegister.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        //create user object
                        User user = new User(email.getText().toString().toLowerCase().trim(),
                                             pass.getText().toString().toLowerCase().trim(),
                                             fName.getText().toString().toLowerCase().trim(),
                                             lName.getText().toString().toLowerCase().trim());

                        //store the user in firebase
                        dbRef.child((user.getEmail()).replace(".", ",")).setValue(user);

                        //end visual effects with a timing handler
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //user feedback
                                Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();

                                //start login activity
                                Intent iLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(iLogin);
                            }
                        }, 1000);

                    }
            }

        });

        //go back on login page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(iRegister);
            }
        });

        //go on home page as a guest
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(iHome);
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

    //handle share action
    private void setShareActionIntent(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
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

    //check if edit text fields are empty
    private boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }

    //Check for duplicate ID (email) in the database
    public boolean isDuplicateID(String userEmail){
        boolean isDupl = true;
        //Check firebase for duplicate ID here
            //...
        isDupl = false;
                //...

        return isDupl;
    }

    //check email pattern
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
