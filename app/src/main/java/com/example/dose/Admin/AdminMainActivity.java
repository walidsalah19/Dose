package com.example.dose.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dose.R;
import com.example.dose.UserAccess.CreateAccount;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {

    private ActivityAdminMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        logout();
    }
    private void logout()
    {
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, Login.class));
            }
        });
    }
}