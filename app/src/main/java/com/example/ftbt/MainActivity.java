package com.example.ftbt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv2,
            tv3, tv31, tv32, tv33, tv34, tv35,
            tv4, tv41, tv42, tv43, tv44, tv45, tv46,
            tv5, tv51, tv52,
            tv6, tv61, tv62, tv63, tv64, tv65, tv66, tv67;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv2 = findViewById(R.id.main_tv2);

        tv3 = findViewById(R.id.main_tv3);
        tv31 = findViewById(R.id.main_tv31);
        tv32 = findViewById(R.id.main_tv32);
        tv33 = findViewById(R.id.main_tv33);
        tv34 = findViewById(R.id.main_tv34);
        tv35 = findViewById(R.id.main_tv35);


        tv4 = findViewById(R.id.main_tv4);
        tv41 = findViewById(R.id.main_tv41);
        tv42 = findViewById(R.id.main_tv42);
        tv43 = findViewById(R.id.main_tv43);
        tv44 = findViewById(R.id.main_tv44);
        tv45 = findViewById(R.id.main_tv45);
        tv46 = findViewById(R.id.main_tv46);


        tv5 = findViewById(R.id.main_tv5);
        tv51 = findViewById(R.id.main_tv51);
        tv52 = findViewById(R.id.main_tv52);

        tv6 = findViewById(R.id.main_tv6);
        tv61 = findViewById(R.id.main_tv61);
        tv62= findViewById(R.id.main_tv62);
        tv63 = findViewById(R.id.main_tv63);
        tv64 = findViewById(R.id.main_tv64);
        tv65= findViewById(R.id.main_tv65);
        tv66 = findViewById(R.id.main_tv66);
        tv67 = findViewById(R.id.main_tv67);


       tv2.setVisibility(View.INVISIBLE);

       tv3.setVisibility(View.INVISIBLE);
       tv31.setVisibility(View.INVISIBLE);
       tv32.setVisibility(View.INVISIBLE);
       tv33.setVisibility(View.INVISIBLE);
       tv34.setVisibility(View.INVISIBLE);
       tv35.setVisibility(View.INVISIBLE);

       tv4.setVisibility(View.INVISIBLE);
       tv41.setVisibility(View.INVISIBLE);
       tv42.setVisibility(View.INVISIBLE);
       tv43.setVisibility(View.INVISIBLE);
       tv44.setVisibility(View.INVISIBLE);
       tv45.setVisibility(View.INVISIBLE);
       tv46.setVisibility(View.INVISIBLE);

       tv5.setVisibility(View.INVISIBLE);
       tv51.setVisibility(View.INVISIBLE);
       tv52.setVisibility(View.INVISIBLE);

       tv6.setVisibility(View.INVISIBLE);
       tv61.setVisibility(View.INVISIBLE);
       tv62.setVisibility(View.INVISIBLE);
       tv63.setVisibility(View.INVISIBLE);
       tv64.setVisibility(View.INVISIBLE);
       tv65.setVisibility(View.INVISIBLE);
       tv66.setVisibility(View.INVISIBLE);
       tv67.setVisibility(View.INVISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv2.setVisibility(View.VISIBLE);
            }
        }, 1200);


                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv3.setVisibility(View.VISIBLE);
                    }
                }, 1500);
                ////////////////////////////////////////

                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv31.setVisibility(View.VISIBLE);
                    }
                }, 1550);
                ////////////////////////////////////////

                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv32.setVisibility(View.VISIBLE);
                    }
                }, 1600);
                ////////////////////////////////////////

                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv33.setVisibility(View.VISIBLE);
                    }
                }, 1650);
                ////////////////////////////////////////

                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv34.setVisibility(View.VISIBLE);
                    }
                }, 1700);
                ////////////////////////////////////////

                ////////////////////////////////////////
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv35.setVisibility(View.VISIBLE);
                    }
                }, 1750);
                ////////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv4.setVisibility(View.VISIBLE);
            }
        }, 1900);
        //////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv41.setVisibility(View.VISIBLE);
            }
        }, 1950);
        //////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv42.setVisibility(View.VISIBLE);
            }
        }, 2000);
        //////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv43.setVisibility(View.VISIBLE);
            }
        }, 2050);
        //////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv44.setVisibility(View.VISIBLE);
            }
        }, 2100);
        //////////////////////////////////////

        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv45.setVisibility(View.VISIBLE);
            }
        }, 2150);
        //////////////////////////////////////
        //////////////////////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv46.setVisibility(View.VISIBLE);
            }
        }, 2200);
        //////////////////////////////////////



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv5.setVisibility(View.VISIBLE);
            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv51.setVisibility(View.VISIBLE);
            }
        }, 2550);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv52.setVisibility(View.VISIBLE);
            }
        }, 2600);

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv6.setVisibility(View.VISIBLE);
            }
        }, 2750);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv61.setVisibility(View.VISIBLE);
            }
        }, 2800);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv62.setVisibility(View.VISIBLE);
            }
        }, 2850);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv63.setVisibility(View.VISIBLE);
            }
        }, 2900);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv64.setVisibility(View.VISIBLE);
            }
        }, 2950);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv65.setVisibility(View.VISIBLE);
            }
        }, 3000);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv66.setVisibility(View.VISIBLE);
            }
        }, 3050);
        /////////

        /////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv67.setVisibility(View.VISIBLE);
            }
        }, 3100);
        /////////



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        }, 4000);

    }

}
