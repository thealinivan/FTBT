package com.example.ftbt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    //Tablayout icons array
    private int[] tabIcons = {
            R.drawable.ic_mainattractions,
            R.drawable.ic_museum,
            R.drawable.ic_greenspaces1
    };

    private ShareActionProvider shareActionProvider;

    //temp buttons
    private Button btn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //temp buttons
        btn = findViewById(R.id.detailsBtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AttractionDetailActivity.class));
            }
        });


        //Attach SectionPagerAdapter to ViewPager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Attach the Viewpager to the Tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        //Add icons to the TabLayout
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

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
