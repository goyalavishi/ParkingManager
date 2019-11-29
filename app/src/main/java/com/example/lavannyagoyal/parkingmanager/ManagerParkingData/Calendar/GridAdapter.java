package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ManagerParkingInfo;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ParkingListAdapter;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today.TodayFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<Events> allEvents;
    private LinearLayout event_date;
    private LinearLayout date_cell;
    private TextView event_id;
    private String currentMonthYear;

    FirebaseFirestore firebaseFirestore;

    Calendar today_date = Calendar.getInstance(Locale.ENGLISH);

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<Events> allEvents,String currentMonthYear) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.currentMonthYear = currentMonthYear;
        mInflater = LayoutInflater.from(context);

    }




    @SuppressLint("WrongViewCast")
    @NonNull
    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        final int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int displayday = currentDate.get(Calendar.DAY_OF_MONTH);
        final int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }

        event_date = (LinearLayout) view.findViewById(R.id.event_date);
        date_cell = (LinearLayout) view.findViewById(R.id.date_cell);
        event_id = (TextView) view.findViewById(R.id.event_id); //revenue of that day

        Log.e("date", String.valueOf(dayValue));
        Log.e("revenue", String.valueOf(event_id.getText()));

        firebaseFirestore=FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("revenue").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<String> ids = new ArrayList<>();
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String id = document.getId();
//                        ids.add(id);
//                    }
//                }
////                ListView listView = (ListView) findViewById(R.id.list_view);
////                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_singlechoice, ids);
////                listView.setAdapter(arrayAdapter);
//            }
//        });

//        firebaseFirestore.collection("revenue").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

        firebaseFirestore.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[displayMonth-1]).collection("datewise").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Log.e("displaymonth",displayMonth+"");
                Log.e(TAG, "inside onComplete");

                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();

                    Log.e(TAG,"task success");


                    for (QueryDocumentSnapshot document : task.getResult()) {


                        int rev = document.getLong("revenue").intValue();

//                        String rev = (String) document.get("revenue");
//                        Log.e("date",document.getId()+"");
                        Log.e("REVREVREV",rev+"");
                        event_id.setText(rev+"");


                        list.add(document.getId());

                        }


                    Log.e("list count", String.valueOf(list.size()));
                    Log.e(TAG, list.toString());

                }



                else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }

            }

        });





//        Toast.makeText(view.getContext(),"display "+displayMonth+"\ncurrent "+currentMonth+"\ntoday "+(today_date.get(Calendar.MONTH)+1),Toast.LENGTH_SHORT).show();


        //Add day to calendar
        final TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));

        date_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("event",""+cellNumber.getText()+" "+currentMonthYear);

//

                ((ManagerDemoActivity)getContext()).switchtotodayfragment(""+cellNumber.getText()+" "+currentMonthYear);

                int dateClicked = Integer.parseInt(String.valueOf(cellNumber.getText()));


//                TodayFragment todayFragment = new TodayFragment();
//                final Context context = parent.getContext();
//                FragmentManager fragmentManager = ((ManagerDemoActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(android.R.id.content, todayFragment);
////                fragmentTransaction.replace(android.R.id.content, todayFragment);
//                fragmentTransaction.commit();


            }
        });

        //Add events to the calendar
        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);


        if(displayMonth == currentMonth && displayYear == currentYear){

        }
        //the cells which do not belong to the month being displayed
        else{
            cellNumber.setTextColor(Color.parseColor("#DFDFDF"));
            eventIndicator.setTextColor(Color.parseColor("#DFDFDF"));
            event_date.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        //today's date
        if(dayValue == displayday && (today_date.get(Calendar.MONTH)+1) == currentMonth && (today_date.get(Calendar.YEAR)) == currentYear)
        {
            cellNumber.setTextColor(Color.parseColor("#FFFFFF"));
        }
        //all other cells
        else{
            event_date.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        //dates after today's date
        if(dayValue > displayday && (today_date.get(Calendar.MONTH)+1) == currentMonth && (today_date.get(Calendar.YEAR)) == currentYear){


            cellNumber.setTextColor(Color.parseColor("#DFDFDF"));
            eventIndicator.setTextColor(Color.parseColor("#DFDFDF"));
        }



        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < allEvents.size(); i++){
            eventCalendar.setTime(ConvertStringToDate(allEvents.get(i).getDATE()));
            if(dayValue >= eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                        arrayList.add(allEvents.get(i).getEVENTS());
                        eventIndicator.setText(arrayList.size()+"REVENUE");
                        eventIndicator.setVisibility(View.GONE);
                        eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
            }

        }
        return view;
    }

    private Date ConvertStringToDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}