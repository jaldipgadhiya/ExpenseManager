package com.android.acadgild.expensemanager;
/*
Activity to display splash screen ( Launcher Activity)
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread mThread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent main = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(main);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
    }
}
