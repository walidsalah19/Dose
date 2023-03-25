package com.example.dose.Doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dose.Admin.AdminMainActivity;
import com.example.dose.R;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.FragmentManageAricalesBinding;


public class ManageArticles extends Fragment {
   private FragmentManageAricalesBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentManageAricalesBinding.inflate(inflater,container,false);

        addArticle();
        return mBinding.getRoot();
    }
    private void addArticle()
    {
        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.doctor, new AddArticle()).addToBackStack(null).commit();

            }
        });
    }
}