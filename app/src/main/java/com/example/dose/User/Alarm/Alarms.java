package com.example.dose.User.Alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Models.Alarm;
import com.example.dose.R;
import com.example.dose.User.Adapter.DisplayAlarms;
import com.example.dose.User.Adapter.DisplayPharmaAdapter;
import com.example.dose.User.Alarm.AddAlarm;
import com.example.dose.User.UserHome;
import com.example.dose.databinding.FragmentAlarmsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Alarms extends Fragment {
    private FragmentAlarmsBinding mBinding;
    private DatabaseReference database;
    private String userId;
    private ArrayList<Alarm> alarms;
    private DisplayAlarms adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAlarmsBinding.inflate(inflater,container,false);
        initFirebase();
        addAlarm();
        back();
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("alarms");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void init()
    {
        alarms=new ArrayList<>();
        adapter=new DisplayAlarms(alarms);
        mBinding.alarms.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.alarms.setAdapter(adapter);
    }
    private void getAlarm()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    alarms.clear();
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String name=snap.child("name").getValue().toString();
                        String time=snap.child("time").getValue().toString();
                        String status=snap.child("status").getValue().toString();
                        String alarmId=snap.child("alarmId").getValue().toString();
                        alarms.add(new Alarm(name,time,status,alarmId,null));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void changeStatus()
    {
        adapter.setOnItemClickListener(new DisplayAlarms.OnItemClickListener() {
            @Override
            public void onclick(int position) {
                String id=alarms.get(position).getAlarmId();
                String status=alarms.get(position).getStatus();
                if (status.equals("on"))
                    status="off";
                else
                    status="on";

                updateStatus(id,status);
            }
        });
    }

    private void updateStatus(String id, String status) {
        database.child(userId).child(id).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new UserHome()).commit();
            }
        });
    }
    private void addAlarm()
    {
        mBinding.addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new AddAlarm()).addToBackStack(null).commit();

            }
        });
    }
}