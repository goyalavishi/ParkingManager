package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lavannyagoyal.parkinghero.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.RecyclerViewHolder> {

    int position;

    CustomCalendarView customCalendarView;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);



    private final Context context;
    private ArrayList<Events> arrayList = new ArrayList<>();

    public CalendarAdapter(Context context, ArrayList<Events> arrayList )
    {
        this.context=context;
        this.arrayList=arrayList;
        Log.e("size",arrayList.size()+"");
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int i) {

        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.month_card,parent,false);
        final RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);

        final TextView month  = (TextView) view.findViewById(R.id.month);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = recyclerViewHolder.getAdapterPosition()+1;

    //            Toast.makeText(view.getContext(),"position = "+position,Toast.LENGTH_SHORT).show();

//                calendar.set(Calendar.MONTH,position);
//                customCalendarView.SetUpCalendar();

//                Toast.makeText(view.getContext(),month.getText()+" "+(recyclerViewHolder.getAdapterPosition()+1),Toast.LENGTH_SHORT).show();



            }
        });
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.RecyclerViewHolder recyclerViewHolder, int position) {

        final Events events = arrayList.get(position);
        recyclerViewHolder.MONTH.setText(events.getMONTH());
        recyclerViewHolder.REVENUE.setText(events.getREVENUE());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView MONTH, REVENUE;
        LinearLayout linearLayout;


        public RecyclerViewHolder(View view){

            super(view);
            MONTH=view.findViewById(R.id.month);
            REVENUE=view.findViewById(R.id.revenue);
            linearLayout=view.findViewById(R.id.linearLayout2);

        }
    }
}
