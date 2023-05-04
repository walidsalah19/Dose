package com.example.dose.User;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dose.Common;
import com.example.dose.R;
import com.example.dose.databinding.FragmentRepeatAlarmBinding;


public class RepeatAlarm extends Fragment {
    private FragmentRepeatAlarmBinding mBinding;
    private int sun=0,mon=0,tue=0,wed=0,thu=0,fri=0,sat=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentRepeatAlarmBinding.inflate(inflater,container,false);
        back();
        mBinding.Sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sun==0)
                {
                    changeColor(mBinding.Sunday,sun,"sunday");
                    sun++;
                }
                else {
                    changeColor(mBinding.Sunday,sun,"sunday");
                    sun=0;
                }
            }
        });
        mBinding.Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mon==0)
                {
                    changeColor(mBinding.Monday,mon,"monday");
                    mon++;
                }
                else {
                    changeColor(mBinding.Sunday,mon,"monday");
                    mon=0;
                }
            }
        }); mBinding.Tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tue==0)
                {
                    changeColor(mBinding.Tuesday,tue,"tuesday");
                    tue++;
                }
                else {
                    changeColor(mBinding.Tuesday,tue,"tuesday");
                    tue=0;
                }
            }
        }); mBinding.Wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wed==0)
                {
                    changeColor(mBinding.Wednesday,wed,"wednesday");
                    wed++;
                }
                else {
                    changeColor(mBinding.Wednesday,wed,"wednesday");
                    wed=0;
                }
            }
        }); mBinding.Thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tue==0)
                {
                    changeColor(mBinding.Thursday,thu,"thursday");
                    tue++;
                }
                else {
                    changeColor(mBinding.Thursday,thu,"thursday");
                    tue=0;
                }
            }
        }); mBinding.Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fri==0)
                {
                    changeColor(mBinding.Friday,fri,"friday");
                    fri++;
                }
                else {
                    changeColor(mBinding.Friday,fri,"friday");
                    fri=0;
                }
            }
        }); mBinding.Saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sat==0)
                {
                    changeColor(mBinding.Saturday,sat,"saturday");
                    sat++;
                }
                else {
                    changeColor(mBinding.Saturday,sat,"saturday");
                    sat=0;
                }
            }
        });






        return mBinding.getRoot();

    }
    private void changeColor(TextView text,int status,String day)
    {
        if (status==0)
        {
            text.setTextColor(Color.parseColor("#036a79"));
            Common.repeat.add(day);
        }
        else {
            text.setTextColor(Color.parseColor("#FF000000"));
            Common.repeat.remove(day);
        }
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(RepeatAlarm.this).commit();
            }
        });
    }
}