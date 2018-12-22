package com.example.bleh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        final Long ID = sharedPreferences.getLong("UserID",-1);
        final Long planId = sharedPreferences.getLong("PlanID",-1);
        Thread myThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);

                Intent intent = new Intent();
                if (ID == -1)
                {
                    intent = new Intent(getApplicationContext(),SignUp.class);
                }
                else
                {
                    intent = new Intent(getApplicationContext(),feature2.class);
                    intent.putExtra("uid",ID);
                    intent.putExtra("planid",planId);
                }
                startActivity(intent);
                finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
