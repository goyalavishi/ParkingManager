package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ManagerId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;

public class ValidateManager extends AppCompatActivity {

    LinearLayout linearLayout ;

    TextView contact_you_no ;
    TextView contact_you_yes ;
    EditText manager_id ;
    Button verify ;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_manager);

        linearLayout = (LinearLayout) findViewById(R.id.registerManager);
        contact_you_no = (TextView) findViewById(R.id.contact_you_no);
        contact_you_yes= (TextView) findViewById(R.id.contact_you_yes);
        manager_id= (EditText) findViewById(R.id.manager_id);
        verify= (Button) findViewById(R.id.verify);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = manager_id.getText().toString();

                if(id.equals("qwerty")){

                    Intent i = new Intent(ValidateManager.this, ManagerDemoActivity.class);
                    startActivity(i);
                    loading();
                   // Toast.makeText(ValidateManager.this, (CharSequence) manager_id,Toast.LENGTH_SHORT).show();
                    finish();

                }
                else {

                    contact_you_yes.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    void loading(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verifying...");
        progressDialog.setMessage("Verifying");
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

    public void onRadioButtonClicked(View view){

        RadioButton registered_yes = (RadioButton) findViewById(R.id.registered_yes);
        RadioButton registered_no = (RadioButton) findViewById(R.id.registered_no);

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.registered_yes:
                if(checked)
                {
                    registered_yes.setTypeface(null, Typeface.BOLD_ITALIC);
                    registered_no.setTypeface(null,Typeface.NORMAL);

                    registered_yes.setTextColor(Color.parseColor("#008080"));
                    registered_no.setTextColor(Color.parseColor("#000000"));

                    manager_id.setVisibility(View.VISIBLE);
                    verify.setVisibility(View.VISIBLE);
//
//                    EditText manager_id = new EditText(this);
//                    manager_id.setHint("Enter your manager ID");
//                    manager_id.setBackgroundResource(R.drawable.boundary2);
//                    manager_id.setPadding(20,20,20,20);
//
//                    Button verify_manager_id = new Button(this);
//                    verify_manager_id.setText("VERIFY");
//                    verify_manager_id.setBackgroundResource(R.drawable.inputbackground);
//                    verify_manager_id.setTextColor(Color.parseColor("#FFFFFF"));
//                    verify_manager_id.setPadding(20,20,20,20);
//
//                    linearLayout.addView(manager_id);
//                    linearLayout.addView(verify_manager_id);
//
                    registered_yes.setEnabled(false);
                    registered_no.setEnabled(false);

                }
                break;

            case R.id.registered_no:
                if(checked)
                {
                    registered_no.setTypeface(null,Typeface.BOLD_ITALIC);
                    registered_yes.setTypeface(null,Typeface.NORMAL);

                    registered_no.setTextColor(Color.parseColor("#008080"));
                    registered_yes.setTextColor(Color.parseColor("#000000"));

                    contact_you_no.setVisibility(View.VISIBLE);

//                    TextView contact_you = new TextView(this);
//                    contact_you.setText("We will contact you soon..");
//                    contact_you.setTextSize(24);
//                    contact_you.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//                    contact_you.setTextColor(Color.parseColor("#000000"));
//
//                    TextView or = new TextView((this));
//                    or.setText("OR");
//                    or.setTextSize(24);
//                    or.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//                    or.setTextColor(Color.parseColor("#000000"));
//
//                    TextView contact_us = new TextView(this);
//                    contact_us.setText("You can contact us : ");
//                    contact_us.setTextSize(24);
//                    contact_us.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//                    contact_us.setTextColor(Color.parseColor("#000000"));
//
//                    TextView website = new TextView(this);
//                    website.setText("www.google.com");
//                    website.setGravity(Gravity.CENTER_HORIZONTAL);
//
//
//
//                    linearLayout.addView(contact_you);
//                    linearLayout.addView(or);
//                    linearLayout.addView(contact_us);
//
                    registered_yes.setEnabled(false);
                    registered_no.setEnabled(false);
                }
        }

    }
}
