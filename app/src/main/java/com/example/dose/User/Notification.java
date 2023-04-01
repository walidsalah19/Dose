package com.example.dose.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.R;
import com.example.dose.databinding.FragmentNotificationBinding;


public class Notification extends Fragment {

   private FragmentNotificationBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentNotificationBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }
}