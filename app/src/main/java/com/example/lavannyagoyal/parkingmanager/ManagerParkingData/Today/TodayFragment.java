package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ManagerParkingInfo;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ParkingListAdapter;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.common.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListenerToday} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    RecyclerView recyclerView;
    ArrayList<TodayModel> parkinglist;
    TodayAdapter todayAdapter;
    RingProgressBar ringProgressBar;
    Calendar calendar2 = Calendar.getInstance(Locale.ENGLISH);
    TextView revenue;
    ImageView emoji;
    TodayModel todayModel;
    EditText search_parkingLot;

    int progress = 0;


    private FirebaseFirestore firebaseFirestore;
    private CollectionReference parkinglots;

    FirebaseFirestore db;

    String name;
    int availableslots, totalspace, costpermonth;

    int rev=0;


    @SuppressLint("HandlerLeak")
    Handler myhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0)
            {
                if (progress<100){
                    progress++;
                    ringProgressBar.setProgress(progress);
                }
            }

            else {
                Message.obtain(myhandler, 0, "Exceeded");
            }
        }
    };

    String address,message,subject;
    int hour,minute;

    ImageButton calendar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListenerToday mListener;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(int param1) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
  //          mParam2 = getArguments().getString(ARG_PARAM2);
        }

        db = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        ringProgressBar=(RingProgressBar) view.findViewById(R.id.today_revenue);
        emoji = (ImageView) view.findViewById(R.id.emoji);

        revenue = (TextView) view.findViewById(R.id.revenue);

        search_parkingLot = (EditText) view.findViewById(R.id.search_parkingLot);

        search_parkingLot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //we are using this method to filter the list after the user has typed in what he wants to search
            @Override
            public void afterTextChanged(Editable s)  //"s" is the content of EditText
            {

                filter(s.toString());
            }
        });


        firebaseFirestore = FirebaseFirestore.getInstance();

        parkinglots = firebaseFirestore.collection("parkingLot");


        todayModel = new TodayModel();

        parkinglist = new ArrayList<>();

        String currentDate = DateFormat.getDateInstance().format(calendar2.getTime());

        final int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int currentDay = Calendar.getInstance().get(Calendar.DATE);



        final String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};


        getActivity().setTitle(monthNames[currentMonth]+" "+currentYear);




        firebaseFirestore.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[currentMonth]).collection("datewise").document(""+currentDay).collection("parkingLot").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "inside onComplete");

                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        //licencePlate = document.getId();
                        name = document.getId();
//                        availableslots = document.getLong("availableslots").intValue();
//                        totalspace = document.getLong("totalspace").intValue();
                        costpermonth = document.getLong("revenue").intValue();

                        rev = rev + costpermonth;

                        Log.e("revenue",""+rev);

                        //Toast.makeText(ManagerParkingInfo.this,licencePlate+"  "+inTime+"  "+outTime,Toast.LENGTH_SHORT).show();
//                        list(""+name,costpermonth,availableslots,totalspace);
                        list(""+name,costpermonth,100 ,500);

                        todayAdapter = new TodayAdapter(getApplicationContext(), parkinglist);
                        recyclerView.setAdapter(todayAdapter);

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("revenue", +rev);
                        db.collection("revenue").document(""+currentYear).collection("months").document(""+monthNames[currentMonth]).collection("datewise").document(""+currentDay).set(userMap);

                    }

                    todayAdapter = new TodayAdapter(getApplicationContext(), parkinglist);
                    recyclerView.setAdapter(todayAdapter);


                    revenue.setText("₹ "+rev);

                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });




        list("ISBT Kashmiri Gate Parking",6000,20,500);
        list("Vishvidhyalaya Gate No. 3",2000,10,250);
        list("Vishvidhyalaya Gate No. 4",2500,13,220);
        list("Fortis Parking",700, 6,100);
        list("chandni chowk",700, 6,100);
        list("igdtuw",700, 6,100);
//


        recyclerView = (RecyclerView) view.findViewById(R.id.todayrecycler);
        @SuppressLint("WrongConstant") LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);



        todayAdapter = new TodayAdapter(getContext(), parkinglist);
        recyclerView.setAdapter(todayAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                       //Toast.makeText(getApplicationContext(),"position="+position,Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(), ManagerParkingInfo.class);
                        i.putExtra("id",position);

                        TodayModel itemClicked = parkinglist.get(position);

                        i.putExtra("parkingname",itemClicked.getParkingname());
                        i.putExtra("revenue",itemClicked.getRevenue());
                        i.putExtra("from_time","00:00");
                        i.putExtra("to_time","23:59");
                       // i.putExtra("date_clicked",);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



      /*  info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getSendEmailIntent(String address, String message, String body);

                //public void getSendEmailIntent(Context context, String address, String subject, String message){
                    Intent email = new Intent(Intent.ACTION_SENDTO);
                    email.putExtra(Intent.EXTRA_EMAIL, address);
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);

//need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
               // }

            }
        });*/

     new Thread(new Runnable() {
         @Override
         public void run() {
             for (int i =0;i<100;i++){
                 try{
                     Thread.sleep(50);
                     myhandler.sendEmptyMessage(0);
                 }catch(InterruptedException e){
                     e.printStackTrace();
                 }
             }
         }
     }).start();
        return view;
    }

    //this method will be called everytime we type in or delete a letter
    //"text" contains what we have typed in to search
    private void filter(String text) {

        ArrayList<TodayModel> filteredList = new ArrayList<>();

        for(TodayModel item : parkinglist){

            //each item from the list is compared with the search keyword
            //both of them are converted to lowercase to ignore case sensitivity
            if (item.getParkingname().toLowerCase().contains(text.toLowerCase()))
            {

                filteredList.add(item);
            }
        }

        todayAdapter.filterList(filteredList);



    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_navigation_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1://item1 is for switching
                Intent intent = new Intent(getActivity(), DemoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListenerToday) {
            mListener = (OnFragmentInteractionListenerToday) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListenerToday {
        // TODO: Update argument type and name
       // void onFragmentInteraction(Uri uri);
    }


    public void timeselect(){
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
              // start_time2.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


        public void getSendEmailIntent(Context context, String address, String subject, String message){
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.putExtra(Intent.EXTRA_EMAIL, address);
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

//need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    void list(String parkingname, int revenue, int available, int total) {
        TodayModel model1= new TodayModel();
        model1.setParkingname(parkingname);
        model1.setRevenue(revenue);
        model1.setAvailableParking(available);
        model1.setCapacity(total);
        parkinglist.add(model1);
    }


    public void changeTitle(CharSequence title){
        getActivity().setTitle(title);
    }
}
