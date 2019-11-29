package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ParkingListAdapter;

import java.util.ArrayList;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.RecyclerViewHolder> {

    private final Context context;
    private ArrayList<TodayModel> todaymodel = new ArrayList<>();
    private OnItemClickListener mlistener;

    public void filterList(ArrayList<TodayModel> filteredList) {

        todaymodel = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public TodayAdapter(Context context, ArrayList<TodayModel> arrayList)
    {
        this.context=context;
        this.todaymodel=arrayList;
        Log.e("size",arrayList.size()+"");
    }

    public void setOnItemClickListener(ParkingListAdapter.OnItemClickListener listener){
        mlistener= (OnItemClickListener) listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_parking_card,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAdapter.RecyclerViewHolder recyclerViewHolder, int position) {

        final TodayModel model = todaymodel.get(position);
        recyclerViewHolder.parkingname.setText(model.getParkingname());
        recyclerViewHolder.revenue2.setText("Collection: ₹ "+model.getRevenue());
        recyclerViewHolder.available2.setText("Available: "+model.availableParking);
        recyclerViewHolder.total2.setText("Total Cars: "+model.capacity);
        

    }

    @Override
    public int getItemCount() {
        return todaymodel.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView parkingname, revenue2, available2, total2;
        LinearLayout linearLayout3;


        public RecyclerViewHolder(View view){

            super(view);
            parkingname=view.findViewById(R.id.parkingname);
            revenue2=view.findViewById(R.id.revenue2);
            available2=view.findViewById(R.id.available2);
            total2=view.findViewById(R.id.total2);
            linearLayout3=view.findViewById(R.id.linearLayout3);

        }
    }
}
