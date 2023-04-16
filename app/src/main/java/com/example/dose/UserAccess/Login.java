package com.example.dose.UserAccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dose.Admin.AdminMainActivity;
import com.example.dose.User.UserMainActivity;
import com.example.dose.databinding.ActivityLoginBinding;
import com.example.dose.pharmaceutical.PharmaceuticalMainActivity;

public class Login extends AppCompatActivity {
   private ActivityLoginBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        login();
        createAccount();
    }
    private void createAccount()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateAccount.class));
            }
        });
    }
    private void login()
    {
     mBinding.login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String email=mBinding.email.getText().toString();
             if (email.equals("admin"))
             {
                 startActivity(new Intent(Login.this, AdminMainActivity.class));
             }
             else if (email.equals("user"))
             {
                 startActivity(new Intent(Login.this, UserMainActivity.class));
             }
             else if (email.equals("pharma"))
             {
                 startActivity(new Intent(Login.this, PharmaceuticalMainActivity.class));
             }
         }
     });
    }
}