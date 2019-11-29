package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register.Mobile_Login;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today.TodayAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
//import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ManagerParkingInfo extends AppCompatActivity {

    private static final String TAG ="ManagerParkingInfo";
    private LineChart mchart;

    private View scroll;

    LineChartView lineChartView;
    String[] axisData = {"6","7","8","9", "10", "11", "12", "01", "02", "03", "04", "05",
            "06", "07", "08","09","10"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 40, 45, 10, 55, 18, 50, 60, 50, 40, 10, 11};

    List yAxisValues;
    List axisValues;

    Button in_time_picker, out_time_picker;
    TextView selected_in_time, selected_out_time;

    Button view_car_logs;

    private int mHour,mMinute;

    ParkingListAdapter parkingListAdapter;
    ArrayList<ParkingData> car_info;
    RecyclerView recyclerView;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    TextView parking_name;
    TextView revenue;
    TextView collection;
    TextView cars_count;
    String parkingname;
    int currentYear;
    int currentMonth;
    int currentDay;

    Button apply_filter;

    String selected_from_time="00:00", selected_to_time="23:59";

    TextView picked_time;

    int rev =0 ;

    String licencePlate, inTime, outTime;
    private int amount;

    private FirebaseFirestore firebaseFirestore;
    FirebaseFirestore db;
    private CollectionReference cardetails;

    String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_parking_info);

        collection = (TextView) findViewById(R.id.collection);
        cars_count = (TextView) findViewById(R.id.cars_count);

        picked_time = (TextView) findViewById(R.id.picked_time);

        apply_filter = (Button) findViewById(R.id.apply_filter);

        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManagerParkingInfo.this,TimeFilter.class);
                startActivity(i);
                finish();
            }
        });

        Intent i = getIntent();
        selected_from_time = i.getStringExtra("from_time");
        //Log.e("revenue",""+selected_from_time);
        selected_to_time = i.getStringExtra("to_time");

        if(selected_from_time.equals(" ")){
            selected_from_time = "00:00";
        }

        if(selected_to_time.equals(" ")){
            selected_to_time = "23:59";
        }

        picked_time.setText(selected_from_time+"   -   "+selected_to_time);


        firebaseFirestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();

        cardetails = firebaseFirestore.collection("cardetails");

        car_info = new ArrayList<>();

        Log.d(TAG, "started");

        getCarLogs(selected_from_time,selected_to_time);

        //  parking_name = (TextView) findViewById(R.id.parking_name);
        revenue = (TextView) findViewById(R.id.revenue);

        scroll = findViewById(R.id.parking_scroll);


        //lineChartView = findViewById(R.id.chart);
        
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",22);
        //String mid = Integer.toString(id);
        parkingname =  getIntent().getStringExtra("parkingname");
        this.setTitle(parkingname);

        //int revenue = (int) intent.getIntExtra("revenue",0);
       // collection.setText("₹ "+revenue);

        // Toast.makeText(this,"position="+id,Toast.LENGTH_SHORT).show();

        // yAxisValues = new ArrayList();
        // axisValues = new ArrayList();

        //display_line();

        mchart = (LineChart) findViewById(R.id.chart);
        //  mchart.setOnChartGestureListener(ManagerParkingInfo.this);
        //  mchart.setOnChartValueSelectedListener(ManagerParkingInfo.this);

        //mchart.setDragEnabled(true);
       // mchart.setScaleEnabled(false);
        //mchart.setOnScrollChangeListener();
        mchart.setScaleEnabled(true);
        mchart.setHorizontalScrollBarEnabled(true);

        ArrayList<Entry>yvalues = new ArrayList<>();

        yvalues.add(new Entry(0,60f));
        yvalues.add(new Entry(1,50f));
        yvalues.add(new Entry(2,40f));
        yvalues.add(new Entry(3,55f));
        yvalues.add(new Entry(4,45f));

        LineDataSet set1 = new LineDataSet(yvalues,"Number of Cars in Hours");
        set1.setFillAlpha(110);
        set1.setColor(Color.parseColor("#008080"));
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        //  set1.setValueTextColors(Collections.singletonList(Color.parseColor("008080")));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mchart.setData(data);

        recyclerView = (RecyclerView) findViewById(R.id.license_recycler);
        @SuppressLint("WrongConstant") LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);


        parkingListAdapter = new ParkingListAdapter(this, car_info);
        recyclerView.setAdapter(parkingListAdapter);

       // storeinfo();


        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentDay = Calendar.getInstance().get(Calendar.DATE);

        Log.e("Current Date",""+currentDate);


        Log.e("date",""+currentDay);
        Log.e("month",""+currentMonth);
        Log.e("month",""+monthNames[currentMonth]);
        Log.e("year",""+currentYear);


        Log.e("Parking Name", ""+parkingname);
        Log.e("Amount",""+amount);


    }

    private void setUpRecyclerView() {

        Query query = cardetails.orderBy("IN", Query.Direction.DESCENDING);


    }

  
  void license_list(String license, String in, String out, int amount){
      ParkingData parkingData= new ParkingData();
      parkingData.setLicenseplateno(license);
      parkingData.setIn(in);
      parkingData.setOut(out);
      parkingData.setCollection(amount);
      car_info.add(parkingData);

  }


  void getCarLogs(final String selected_from_time, final String selected_to_time){
//       firebaseFirestore.collection("data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      //firebaseFirestore.collection("revenue").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


      currentMonth = Calendar.getInstance().get(Calendar.MONTH);
      currentYear = Calendar.getInstance().get(Calendar.YEAR);
      currentDay = Calendar.getInstance().get(Calendar.DATE);

      parkingname = getIntent().getStringExtra("parkingname");

     // Log.e("PATH","revenue "+currentYear+" months "+monthNames[currentMonth]+" datewise "+currentDay+" parkingLot "+parkingname+" cars");

      firebaseFirestore.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[currentMonth]).collection("datewise").document(""+currentDay).collection("parkingLot").document(parkingname).collection("cars").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              Log.d(TAG, "inside onComplete");

              if (task.isSuccessful()) {
                  List<String> list = new ArrayList<>();

                  rev = 0;

                  for (QueryDocumentSnapshot document : task.getResult()) {
                      licencePlate = document.getId();
                      inTime = (String) document.get("IN");
                      outTime = (String) document.get("OUT");
                      amount = document.getLong("collection").intValue();

//                      Log.e("PATH","revenue "+currentYear+" months "+monthNames[currentMonth]+" datewise "+currentDay+" parkingLot "+parkingname+" cars");


                     // Log.e("current year",""+currentYear);
                     // Log.e("current month",""+ currentMonth);
                     // Log.e("current day", ""+currentDay);
                    //  Log.e("Parking Name", ""+parkingname);


                      if(inTime.compareTo(selected_from_time)>=0 && inTime.compareTo(selected_to_time)<=0){

                          list.add(document.getId());

                          license_list(""+licencePlate,""+inTime,""+outTime,amount);
                              rev+=amount;

                      }



                      parkingListAdapter = new ParkingListAdapter(getApplicationContext(), car_info);
                      recyclerView.setAdapter(parkingListAdapter);

                  }

                  setUpRecyclerView();

                  parkingListAdapter = new ParkingListAdapter(getApplicationContext(), car_info);
                  recyclerView.setAdapter(parkingListAdapter);

                  cars_count.setText(""+list.size());
                  collection.setText("₹ "+rev);

                  Map<String, Object> userMap = new HashMap<>();
                  userMap.put("revenue", +rev);
                  db.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[currentMonth]).collection("datewise").document(""+currentDay).collection("parkingLot").document(parkingname).set(userMap);



                  Log.e("list count", String.valueOf(list.size()));
                  Log.e(TAG, list.toString());


              } else {
                  Log.d(TAG, "Error getting documents: ", task.getException());
              }
          }
      });

  }

 /* void storeinfo(){

      userMap.put("revenue", rev);
      db.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[currentMonth]).collection("datewise").document(""+currentDay).set(userMap);


  }*/



}

