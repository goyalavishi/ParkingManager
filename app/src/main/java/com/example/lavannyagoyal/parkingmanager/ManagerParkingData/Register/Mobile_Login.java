package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Mobile_Login extends AppCompatActivity {

    private static final String TAG = "Mobile_Login";
    private EditText phone_number;
    private String phoneNumber;
    private Button next_btn;

    private String verificationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__login);

        phone_number = (EditText) findViewById(R.id.phone_number);
        next_btn = (Button) findViewById(R.id.next_btn);

        signout();


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                phoneNumber = String.valueOf(phone_number.getText());

                if(phoneNumber.isEmpty() || phoneNumber.length()<10){
                    phone_number.setError("Invalid Phone Number");
                    phone_number.requestFocus();
                    return;
                }

                phoneNumber = "+91"+phoneNumber;


                Intent i = new Intent(Mobile_Login.this, OTP_verification.class);
                i.putExtra("phoneNumber",phoneNumber);
                startActivity(i);
            }
        });

//        setPhoneNumber();

    }

    private void signout() {


        FirebaseAuth.getInstance().signOut();


        Toast.makeText(Mobile_Login.this,"signout",Toast.LENGTH_SHORT).show();

    }

//    private void setPhoneNumber(){
//        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//        try {
//            phone_number.setText(user.getPhoneNumber());
//        }catch (Exception e){
//            Toast.makeText(this,"Phone number not found",Toast.LENGTH_SHORT).show();
//        }
//    }




}
