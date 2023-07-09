package com.doseApp.dose.dose.User.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doseApp.dose.dose.Models.Alarm;
import com.doseApp.dose.dose.R;

import java.util.ArrayList;

public class DisplayAlarms extends RecyclerView.Adapter<DisplayAlarms.help>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onclick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    private ArrayList<Alarm> alarms;

    public DisplayAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alarms,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        String time=alarms.get(position).getHours()+":"+alarms.get(position).getMinute();
            holder.alarm.setText(time+" "+alarms.get(position).getType());

        if (alarms.get(position).getStatus().equals("on"))
        {
            holder.alarm.setChecked(true);
        }
        holder.alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class help extends RecyclerView.ViewHolder
    {

        Switch alarm;
        public help(@NonNull View itemView) {
            super(itemView);
            alarm=itemView.findViewById(R.id.switchTime);
        }
    }
}