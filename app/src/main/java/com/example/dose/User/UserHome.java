package com.example.dose.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.R;
import com.example.dose.databinding.FragmentUserHomeBinding;

public class UserHome extends Fragment {
    private FragmentUserHomeBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentUserHomeBinding.inflate(inflater,container,false);
        health();
        chat();
        check();
        alarm();
        notification();
        return mBinding.getRoot();
    }
    private void notification()
    {
        mBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.notificationLayout, new Notification()).addToBackStack(null).commit();

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
                Treatment t=new Treatment();
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