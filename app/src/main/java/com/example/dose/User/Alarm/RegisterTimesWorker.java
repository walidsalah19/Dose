package com.example.dose.User.Alarm;


import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dose.Common;
import com.example.dose.Models.Alarm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RegisterTimesWorker extends Worker {

    public RegisterTimesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Result doWork() {
        try {
            Calendar calendar = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                calendar = Calendar.getInstance();
            }
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE) ;
            int  seconds= calendar.get(Calendar.SECOND) ;
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) ;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            ArrayList<Alarm> times=new ArrayList<>();
            Log.e("hour",hour+"");
            DatabaseReference data= FirebaseDatabase.getInstance().getReference("alarms");
            String userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
            data.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        for (DataSnapshot snap:snapshot.getChildren())
                        {
                            String status=snap.child("status").getValue().toString();
                            if (status.equals("on")) {
                                String name=snap.child("name").getValue().toString();
                                String hours=snap.child("hours").getValue().toString();
                                String minute=snap.child("minute").getValue().toString();
                                String alarmId=snap.child("alarmId").getValue().toString();
                                String type=snap.child("type").getValue().toString();
                                times.add(new Alarm(name,hours,minute,status,alarmId, type, null));

                            }
                        }
                        alarm(times,year,month,day,hour,minute,seconds);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return Result.failure();
        }catch (Exception e)
        {
            return Result.retry();
        }
    }

    private void alarm(ArrayList<Alarm> times, int year, int month, int day, int hour, int minute, int seconds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            times.forEach(s ->{
                String time="";
                if (s.getType().equals("pm"))
                {
                    int h=Integer.parseInt(s.getHours())+12;
                    time=h+":"+s.getMinute();
                }
                else {
                    time=s.getHours()+":"+s.getMinute();
                }
                String selectTime= time+":"+"00";
                String alarmTime = "" + year + "/" + month + "/" + day + " " +s.getName()+time;//unique for every alarm
                String currentTime=hour+":"+minute+":"+seconds;
                long delay = Common.calculateDelay(currentTime, selectTime);
                Log.e("delay",delay+"");
                Log.e("current time",currentTime+"");
                Log.e("alarm time",time+"");

                if (delay > 0) {
                    Data input = new Data.Builder()
                            .putString("title", "Dose")
                            .putString("content", s.getName())
                            .build();

                    OneTimeWorkRequest registerPrayerRequest = new OneTimeWorkRequest
                            .Builder(AlarmNotification.class)
                            .addTag(alarmTime)
                            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                            .setInputData(input)
                            .build();

                    WorkManager.getInstance(getApplicationContext())
                            .enqueueUniqueWork(alarmTime, ExistingWorkPolicy.REPLACE, registerPrayerRequest);
                }
            });
        }

    }
}

