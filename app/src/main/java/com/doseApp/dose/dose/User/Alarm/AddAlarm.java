package com.doseApp.dose.dose.User.Alarm;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.doseApp.dose.dose.Common;
import com.doseApp.dose.dose.Models.Alarm;
import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.User.RepeatAlarm;
import com.doseApp.dose.dose.ViewModelData;
import com.doseApp.dose.dose.databinding.FragmentAddAlarmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddAlarm extends Fragment {
    private FragmentAddAlarmBinding mBinding;
    private DatabaseReference database;
    private String userId;
    private ViewModelData repeat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAddAlarmBinding.inflate(inflater,container,false);
        repeat=new ViewModelProvider(getActivity()).get(ViewModelData.class);
        initFirebase();
        repeat();
        addAlarm();
        back();

        repeat.getRepeat().observe(getActivity(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if (Common.repeat.isEmpty())
                {
                    mBinding.text3.setText("Every Day");
                }
                else {
                    String r="";
                    for(String d:Common.repeat)
                    {
                        r+=d.charAt(0)+" ";
                        mBinding.text3.setText("Every "+r);
                    }
                }
            }
        });
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("alarms");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void addAlarm()
    {
        mBinding.addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mBinding.name.getText().toString();
                if (name.isEmpty())
                {
                    mBinding.name.setError("enter the alarm name");
                }
                else {
                    int hour = 0;
                    int minute = 0;
                    String type="am";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hour = mBinding.datePicker1.getHour();
                        minute = mBinding.datePicker1.getMinute();
                        if (hour>12)
                        {
                            hour-=12;
                            type="pm";
                        }
                        else if (hour==12)
                        {
                            type="pm";
                        }
                    }
                    String id= UUID.randomUUID().toString();
                    Alarm a=new Alarm(name,hour+"",minute+"","on",id, type, Common.repeat);
                    addToMyAlarms(a);
                }
            }
        });
    }

    private void addToMyAlarms(Alarm a) {
        database.child(userId).child(a.getAlarmId()).setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    createAlarm();
                    successSweetDialog();
                }
            }
        });
    }
    private void successSweetDialog()
    {
        SweetAlertDialog success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText("add Alarm successfully ");
        success.show();
    }
    private void createAlarm()
    {
        WorkManager.getInstance(getActivity().getApplicationContext()).cancelAllWork();
        PeriodicWorkRequest request=new PeriodicWorkRequest
                .Builder(RegisterTimesWorker.class,1, TimeUnit.DAYS)
                .addTag("Alarm")
                .build();
        WorkManager.getInstance(getActivity().getApplicationContext()).enqueueUniquePeriodicWork("Alarm", ExistingPeriodicWorkPolicy.REPLACE,request);

    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new Alarms()).commit();
            }
        });
    }

    private void repeat()
    {

        mBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.userFrameLayout, new RepeatAlarm()).addToBackStack(null).commit();
            }
        });
    }
}