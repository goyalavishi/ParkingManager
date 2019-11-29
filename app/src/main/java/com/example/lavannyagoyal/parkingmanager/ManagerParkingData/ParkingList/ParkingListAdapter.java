package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home.ManagerDemoActivity;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.ParkingData;

import java.util.ArrayList;

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListAdapter.ParkingListHolder> {

    Context context;
    ArrayList<ParkingData> parkingData;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public ParkingListAdapter(Context context, ArrayList<ParkingData> parkingData) {

        this.parkingData=parkingData;
        this.context=context;

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }


    @NonNull
    @Override
    public ParkingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_manager_parking_list,parent,false);

        ParkingListHolder parkingListHolder=new ParkingListHolder(view);
        return parkingListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingListHolder parkingListHolder, int i) {

        final int ii=i;

        parkingListHolder.license_no.setText(""+parkingData.get(i).getLicenseplateno());
        parkingListHolder.in.setText("IN : "+parkingData.get(i).getIn());
        parkingListHolder.out.setText("OUT : "+parkingData.get(i).getOut());
        parkingListHolder.amount.setText("Collection : "+parkingData.get(i).getCollection());

       /* parkingListHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // parkingListHolder.linearLayout.setBackgroundColor(Color.parseColor("#7F000000"));
                Intent intent=new Intent(context, ManagerDemoActivity.class);
                intent.putExtra("Parkingid",parkingData.get(ii).getParkingid()+"");
                context.startActivity(intent);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return parkingData.size();
    }


    public static class ParkingListHolder extends RecyclerView.ViewHolder
    {
        TextView  license_no;
        TextView in;
        TextView out;
        TextView amount;
        LinearLayout linearLayout;

        public ParkingListHolder(View itemView)
        {
            super(itemView);
            license_no=(TextView)itemView.findViewById(R.id.license_plate_no);
            in=(TextView)itemView.findViewById(R.id.in_time);
            out=(TextView) itemView.findViewById(R.id.out);
            amount=(TextView) itemView.findViewById(R.id.cash_collected);
            linearLayout= itemView.findViewById(R.id.parking_info);
        }

    }

}
