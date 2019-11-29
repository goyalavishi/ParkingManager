package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_verification extends AppCompatActivity {

    private String phoneNumber;
    private TextView veri_code_details;
    private EditText veri_code;
    private Button verify_btn;
    private Button resend_btn;

    private FirebaseAuth auth;

    String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        veri_code_details = (TextView) findViewById(R.id.veri_code_details);
        veri_code = (EditText) findViewById(R.id.veri_code);
        verify_btn = (Button) findViewById(R.id.verify_btn);
        resend_btn = (Button) findViewById(R.id.resend_btn);

        auth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        phoneNumber = i.getStringExtra("phoneNumber");

        veri_code_details.append(phoneNumber);

        sendOTP(phoneNumber);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = veri_code.getText().toString().trim();

                if(otp.isEmpty() || otp.length()<6){

                    veri_code.setError("Invalid OTP");
                    veri_code.requestFocus();
                    return;

                }

                verifyOTP(otp);


            }
        });

        resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP(phoneNumber);
            }
        });

    }

    private void sendOTP(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                OTP_verification.this,
                mCallback);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            //code is detected automatically
            if(code!=null){

                veri_code.setText(code);

                verifyOTP(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(OTP_verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCode = s;

            Toast.makeText(OTP_verification.this,"OTP sent",Toast.LENGTH_SHORT).show();

        }


    };

    private void verifyOTP(String otp) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
        signInWithCredential(credential);


    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    Toast.makeText(OTP_verification.this,"successful",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(OTP_verification.this, ManagerDemoActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(OTP_verification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}