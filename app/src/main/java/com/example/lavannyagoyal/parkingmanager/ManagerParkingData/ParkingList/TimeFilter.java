package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today.TodayModel;

import java.sql.Time;
import java.util.Calendar;

public class TimeFilter extends AppCompatActivity {

    TextView from_time, to_time;
    ImageView from_time_picker, to_time_picker;

    Button save_filter;

    int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_filter);

        from_time = (TextView) findViewById(R.id.from_time);
        to_time = (TextView) findViewById(R.id.to_time);

        from_time_picker = (ImageView) findViewById(R.id.from_time_picker);
        to_time_picker = (ImageView) findViewById(R.id.to_time_picker);

        save_filter = (Button) findViewById(R.id.save_filter);

        from_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimeFilter.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String hh = String.valueOf(hourOfDay);
                                String mm = String.valueOf(minute);

                                if(hourOfDay<10){
                                    hh = "0"+hh;
                                }

                                if(minute<10){
                                    mm = "0"+mm;
                                }

                                from_time.setText(hh+":"+mm);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        to_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimeFilter.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String hh = String.valueOf(hourOfDay);
                                String mm = String.valueOf(minute);

                                if(hourOfDay<10){
                                    hh = "0"+hh;
                                }

                                if(minute<10){
                                    mm = "0"+mm;
                                }

                                to_time.setText(hh+":"+mm);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        save_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimeFilter.this,ManagerParkingInfo.class);

                i.putExtra("from_time",from_time.getText().toString());
                i.putExtra("to_time",to_time.getText().toString());
                String parkingname =  getIntent().getStringExtra("parkingname");
                TimeFilter.this.setTitle(parkingname);
                startActivity(i);
                finish();
            }
        });
    }
}
