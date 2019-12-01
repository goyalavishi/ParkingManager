package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register.Login_Signup;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register.Mobile_Login;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.common.MyPreferences;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT =2000 ;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        final MyPreferences myPrefences=new MyPreferences(this);
       getSupportActionBar().hide();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if(myPrefences.isNotLogin())
                    {
                        Intent i = new Intent(SplashScreen.this, Mobile_Login.class);
                        startActivity(i);
                    }

                    else
                    {

                            Intent i = new Intent(SplashScreen.this, ManagerDemoActivity.class);
                            startActivity(i);
                    }
                }
            }
        }.start();

        logo = findViewById(R.id.logo);
        logo.setColorFilter(getResources().getColor(R.color.white));
    }

   /* public void onClick(View v) {
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
        finish();
    }*/
}