package com.example.dose.pharmaceutical;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Chat.ChatFragment;
import com.example.dose.Common;
import com.example.dose.R;
import com.example.dose.databinding.FragmentDisplayUsersBinding;

public class DisplayUsers extends Fragment {

    private FragmentDisplayUsersBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentDisplayUsersBinding.inflate(inflater,container,false);

        chat();
        return mBinding.getRoot();
    }
    private void chat()
    {
        mBinding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.type=1;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.pharma, new ChatFragment()).addToBackStack(null).commit();

            }
        });
    }
}