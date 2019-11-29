package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.common.RecyclerItemClickListener;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar.CalendarAdapter;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar.CustomCalendarView;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar.Events;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalenderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    ArrayList<Events> monthlist;
    CalendarAdapter calendarAdapter;
   CustomCalendarView customCalendarView;

    int currentYear = Calendar.getInstance().get(Calendar.YEAR);


    LinearLayout date_cell;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListenerCalendar mListener;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderFragment newInstance(int param1) {
        CalenderFragment fragment = new CalenderFragment();
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
            //         mParam1 = getArguments().getString(ARG_PARAM1);
           //          mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);


        customCalendarView = (CustomCalendarView) view.findViewById(R.id.custom_calendar_view);

        monthlist = new ArrayList<>();


        settings("Jan","3.1k");
        settings("Feb","2.6k");
        settings("Mar","2.4k");
        settings("Apr","2.7k");
        settings("May","2.5k");
        settings("Jun","2.7k");
        settings("Jul","2.3k");
        settings("Aug","2.9k");
        settings("Sep","3.2k");
        settings("Oct","4.3k");
        settings("Nov","3.6k");
        settings("Dec","3.8k");

        recyclerView = (RecyclerView) view.findViewById(R.id.calendarrecyclerview);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);



        calendarAdapter = new CalendarAdapter(getContext(), monthlist);
        recyclerView.setAdapter(calendarAdapter);


        recyclerView.scrollToPosition(3);
        calendarAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(),recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onItemClick(View view, int position) {



                        customCalendarView.calendar.set(Calendar.MONTH,position);

                    //    customCalendarView.CurrentDate.setText(customCalendarView.monthNames[position]+"");

                        Log.e("--------------------------",customCalendarView.monthNames[position]+"");

                        customCalendarView.SetUpCalendar(position);
                        recyclerView.scrollToPosition(position-2);
                        calendarAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        View otherView = (View) inflater.inflate(R.layout.single_cell_layout,null);


        date_cell = (LinearLayout) otherView.findViewById(R.id.date_cell);

        date_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(),"works",Toast.LENGTH_SHORT).show();
                Log.e("fragment","works");

                mListener.sendData("Hello");
            }
        });

        return view;
    }

    //Switch to user
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


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListenerCalendar) {
            mListener = (OnFragmentInteractionListenerCalendar) context;
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
    public interface OnFragmentInteractionListenerCalendar {
        // TODO: Update argument type and name
     //   void onFragmentInteraction(Uri uri);

        void sendData(String data);
    }

    void settings(String month, String revenue) {
        Events events1 = new Events();
        events1.setMONTH(month);
        events1.setREVENUE(revenue);
        monthlist.add(events1);
    }
}
