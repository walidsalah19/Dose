package com.example.dose.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.dose.R;
import com.example.dose.TreatmentFragment;
import com.example.dose.User.Alarm.Alarms;
import com.example.dose.databinding.FragmentUserHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHome extends Fragment {
    private FragmentUserHomeBinding mBinding;
    private DatabaseReference database;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentUserHomeBinding.inflate(inflater,container,false);
       initFirebase();
        health();
        chat();
        check();
        alarm();
        notification();
        removeNotification();
        return mBinding.getRoot();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("users");
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        getUserData();
    }
    private void getUserData()
    {
        database.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("userName").getValue().toString();
                mBinding.userName.setText(name);
                if (snapshot.child("image").exists() && !snapshot.child("image").getValue().toString().equals(""))
                {
                    String im=snapshot.child("image").getValue().toString();
                    Glide.with(UserHome.this).load(im).into(mBinding.profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void notification()
    {
        mBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.notificationLayout.setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.notificationLayout, new Notification()).addToBackStack(null).commit();

            }
        });
    }
    private void removeNotification()
    {
        mBinding.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.notificationLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void health()
    {
        mBinding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToFragment(new HealthRecord());
            }
        });
    }
    private void check()
    {
        mBinding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("type","2");
                TreatmentFragment t=new TreatmentFragment();
                t.setArguments(b);
                moveToFragment(t);
            }
        });
    }
    private void alarm()
    {
        mBinding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToFragment(new Alarms());
            }
        });
    }
    private void chat()
    {
        mBinding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToFragment(new DisplayPharma());
            }
        });
    }
    private void moveToFragment(Fragment fragment) {
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, fragment).addToBackStack(null).commit();
    }
}