package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lavannyagoyal.parkinghero.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class CustomCalendarView extends LinearLayout {
    ImageView previousButton, nextButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDARS_DAYS= 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    AlertDialog alertDialog;

    GridAdapter gridAdapter;

    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();
    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        SetUpCalendar(currentMonth);


        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);

                --currentMonth;

                if(currentMonth==-1){
                    currentMonth=11;
                    --currentYear;
                }

                SetUpCalendar(currentMonth);
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                calendar.add(Calendar.MONTH,1);

                ++currentMonth;

                if(currentMonth==12){
                    currentMonth=0;
                    ++currentYear;
                }

                SetUpCalendar(currentMonth);
            }
        });

    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = view.findViewById(R.id.previous_month);
        nextButton = view.findViewById(R.id.next_month);
        CurrentDate = (TextView)view.findViewById(R.id.display_current_date);
        gridView = (GridView)view.findViewById(R.id.calendar_grid);
    }

    public void SetUpCalendar(int currentMonth){
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        if(currentMonth <= Calendar.getInstance().get(Calendar.MONTH) && currentYear <= Calendar.getInstance().get(Calendar.YEAR))
        {
            if(currentMonth == Calendar.getInstance().get(Calendar.MONTH) && currentYear == Calendar.getInstance().get(Calendar.YEAR))
            {
                nextButton.setEnabled(false);
                nextButton.setColorFilter(Color.parseColor("#DFDFDF"));
            }
            else{
                nextButton.setEnabled(true);
                nextButton.setColorFilter(Color.parseColor("#008080"));
            }


            this.currentMonth = currentMonth;

            CurrentDate.setText(monthNames[currentMonth]+" "+currentYear);

            Log.e("--------------------",monthNames[currentMonth]+"");

            dates.clear();
            Calendar monthCalendar = (Calendar) calendar.clone();
            monthCalendar.set(Calendar.DAY_OF_MONTH,1);
            int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
            monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayofMonth);
            Collectrevenue(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

            while (dates.size() < MAX_CALENDARS_DAYS){
                dates.add(monthCalendar.getTime());
                monthCalendar.add(Calendar.DAY_OF_MONTH,1);
            }

            gridAdapter = new GridAdapter(context,dates,calendar,eventsList,"- "+(currentMonth+1)+" - "+currentYear);
            gridView.setAdapter(gridAdapter);



        }



    }

    //Revenue Collected
    private void Collectrevenue(String Month,String year){
        eventsList.clear();

    }
}
