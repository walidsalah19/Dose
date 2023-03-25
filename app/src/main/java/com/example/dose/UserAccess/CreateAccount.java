package com.example.dose.UserAccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dose.R;
import com.example.dose.databinding.ActivityCreateAccountBinding;

public class CreateAccount extends AppCompatActivity {
 private ActivityCreateAccountBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        haveAccount();
    }
    private void haveAccount()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, Login.class));
            }
        });
    }
}