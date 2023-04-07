package com.example.dose.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Chat.ChatFragment;
import com.example.dose.R;
import com.example.dose.databinding.FragmentAddAlarmBinding;


public class AddAlarm extends Fragment {
    private FragmentAddAlarmBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAddAlarmBinding.inflate(inflater,container,false);
        repeat();
        back();
        return mBinding.getRoot();
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