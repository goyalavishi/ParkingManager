package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;

import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.common.MyPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Login_Signup extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_section;
    private Button Signout;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private SignInButton SignIn;
    private Button Login;
    private View mScrollView;
    private TextView Signup;
    private TextView skip;
    private static final String TAG = "";
    private static final int REQ_CODE = 9901;
    GoogleApiClient googleApiClient;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    RadioGroup radioGroup;
    List<Users> usersList;
    MyPreferences myPrefences;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db= FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__signup);

        usersList= new ArrayList<>();

        myPrefences= new MyPreferences(this);
        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {

            checkuid();
            call();
        }

        //skip = findViewById(R.id.skip);
        editTextEmail = findViewById(R.id.editText);
        editTextPassword = findViewById(R.id.editText2);
        Login = findViewById(R.id.button2);
        Signup = findViewById(R.id.signup);
        Login.setOnClickListener(this);

        mScrollView = findViewById(R.id.scroll);
//       skip.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//               Intent i = new Intent(Login_Signup.this,DemoActivity.class)startActivity(i);
//
//            }
//        });

        //skip.setVisibility(View.GONE);

       /* Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signup();
            }
        });*/
    }

    private void userLogin(){
        String getEmailId = editTextEmail.getText().toString().trim();
        String getPassword = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(getEmailId)) {
            Toast.makeText(getApplicationContext(), "Enter Eamil Id", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(getPassword)) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(getEmailId,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    loading();

                    checkuid();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect Email id or Password! \nOr if not signed up, Please Sign up first! ", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

   /* void signup()
    {
        Intent i = new Intent(Login_Signup.this,Sign_Up.class);
        startActivity(i);
    }*/

    void checkuid()
    {

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
                }

                else {

                    List<DocumentSnapshot> documentSnapshots=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {

                        UserResponse userResponse=documentSnapshot.toObject(UserResponse.class);

                        if(userResponse.getId().equals(firebaseAuth.getCurrentUser().getUid())) {
                            Log.e("usertype=", userResponse.getUsertype());
                            myPrefences.setTypeOfUser(userResponse.getUsertype());
                            call();
                        }

                    }
                }
            }


        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

    }

    void call()
    {

        myPrefences.setIsNotLogin(false);
        /*Log.e("usertype in call func=", myPrefences.getTypeOfUser());
        if (myPrefences.getTypeOfUser().equals("u"))  {
            Log.e("user=","u");
            Intent i = new Intent(this, NumberPlate.class);
            i.putExtra("position",-1);
            startActivity(i);
            finish();
        } else {*/
            Log.e("user=","m");
            Intent i = new Intent(this, ManagerDemoActivity.class);
            startActivity(i);
            finish();
        //}


    }

    void loading(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // progressDialog.dismiss();
            }
        }).start();
        progressDialog.show();

    }


    @Override
    public void onClick(View view) {
        if(view == Login){
            userLogin();
            Log.d(TAG,"ES");
        }
    }


    //Radio Button functionality

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch (view.getId()) {
//
//            case R.id.user:
//                if (checked) {
//                    myPreferences.setTypeOfUser("1");
//
//                }
//                break;
//
//            case R.id.manager:
//                if (checked)
//                    myPreferences.setTypeOfUser("2");
//                break;
//        }
//    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()){
            if (progressDialog!= null) {
                progressDialog.dismiss();
                progressDialog= null;
            }
        }
    }
}