package com.example.dose.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.R;
import com.example.dose.databinding.FragmentUserProfileBinding;

public class UserProfile extends Fragment {

    private FragmentUserProfileBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentUserProfileBinding.inflate(inflater,container,false);


        return mBinding.getRoot();
    }
}