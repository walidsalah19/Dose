package com.example.dose.Chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Doctor.AddArticle;
import com.example.dose.R;
import com.example.dose.databinding.FragmentChatBinding;
import com.example.dose.pharmaceutical.DisplayUsers;


public class ChatFragment extends Fragment {


    private FragmentChatBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentChatBinding.inflate(inflater,container,false);

        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.pharma,new DisplayUsers()).commit();

            }
        });
    }
}