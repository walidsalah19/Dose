package com.example.dose.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Models.User;
import com.example.dose.R;
import com.example.dose.User.Adapter.DisplayPharmaAdapter;
import com.example.dose.User.Adapter.NotificationAdapter;
import com.example.dose.databinding.FragmentNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Notification extends Fragment {

   private FragmentNotificationBinding mBinding;
    private DatabaseReference database;
    private NotificationAdapter adapter;
    private ArrayList<String> notify;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentNotificationBinding.inflate(inflater,container,false);
        initFirebase();
        init();
        getNotify();
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("notification");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }
    private void init()
    {
        notify=new ArrayList<>();
        adapter=new NotificationAdapter(notify);
        mBinding.notification.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.notification.setAdapter(adapter);
    }
    private void getNotify()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot data:snapshot.getChildren())
                    {
                        String n=data.child("notify").getValue().toString();
                        notify.add(n);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    mBinding.notification.setVisibility(View.GONE);
                    mBinding.notify.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}