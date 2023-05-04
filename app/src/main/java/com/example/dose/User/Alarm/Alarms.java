package com.example.dose.User.Alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Models.Alarm;
import com.example.dose.R;
import com.example.dose.User.Alarm.AddAlarm;
import com.example.dose.User.UserHome;
import com.example.dose.databinding.FragmentAlarmsBinding;
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
    private void getAlarm()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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