package com.example.bands4hire.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.bands4hire.Activities.MainActivity;
import com.example.bands4hire.R;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash_screen);


        //just lets the screen sleep for 3 seconds adn then launches the main acitivity.  This is the first activity to launch
        Thread sleepThread = new Thread(){
            public void run(){
                try{
                    sleep(6000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                }
            }
        };
        sleepThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}