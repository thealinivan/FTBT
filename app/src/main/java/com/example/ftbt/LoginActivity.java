package com.example.ftbt;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //login token and ID(for retrieving user data)
    public static boolean token = false;

    //text fields
    private EditText email, pass;

    //share
    private ShareActionProvider shareActionProvider;

    //buttons
    private Button btnLogin, btnRegister, btnGuest;

    //progress bar
    private ProgressBar progressBar;

    private static final String TAG = "FIREBASE:GET USER";

    //for user queries
    private Query qRef;
    private RecyclerView rv;
    private static ArrayList<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up button on app bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //text fields
        email = findViewById(R.id.login_email);
        pass = findViewById(R.id.login_password);

        //buttons
        btnLogin = findViewById(R.id.login_loginBtn);
        btnRegister = findViewById(R.id.login_registerBtn);
        btnGuest = findViewById(R.id.login_guestBtn);

        //progress bar
        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check text fields and if filled get user from database into ArrayList
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    qRef = FirebaseDatabase.getInstance().getReference("Users")
                            .getRef()
                            .orderByChild("email")
                            .equalTo(email.getText().toString().toLowerCase().trim());
                    qRef.addListenerForSingleValueEvent(listener);
                }

                //Check if email and password are matching
                        if (!(mAuth(email.getText().toString(), (pass.getText().toString())))) {
                            email.setText("");
                            pass.setText("");
                            Toast.makeText(LoginActivity.this, "Wrong Email or Password!", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //display progress bar
                            btnLogin.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            //Handle authentication
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    //user welcome feedback
                                    Toast.makeText(LoginActivity.this, "Welcome ", Toast.LENGTH_SHORT).show();

                                    //chamge activity
                                    Intent iRegister = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(iRegister);

                                }
                            }, 1000);
                        }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(iRegister);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
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

    //Handle share action
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

    public boolean mAuth(String email, String password) {
        if (list.size() > 0) {
            User user = list.get(0);
            if ((user.getEmail()).equals(email) && ((user.getPassword()).equals(password))) {
                token = true;
            }
        }
        return token;
    }


    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            for (DataSnapshot dss:dataSnapshot.getChildren()){
                User user = dss.getValue(User.class);
                list.add(user);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    //check if edit text fields are empty
    private boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }

    //return logged in user object
    public static User getCurrentUser(){
        return list.get(0);
    }


}
