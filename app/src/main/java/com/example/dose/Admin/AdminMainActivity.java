package com.example.dose.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.dose.Models.User;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.ActivityAdminMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminMainActivity extends AppCompatActivity {

    private ActivityAdminMainBinding mBinding;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initFirebase();
        clickAddPharma();
        logout();
    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("pharma");
    }
    private void clickAddPharma()
    {
        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPharmaData();
            }
        });
    }

    private void checkPharmaData() {
        String email=mBinding.email.getText().toString();
        String password=mBinding.password.getText().toString();
        String userName=mBinding.username.getText().toString();

         if (TextUtils.isEmpty(userName))
        {
            mBinding.username.setError("please enter pharmaceutical name ");
        }
        else if (TextUtils.isEmpty(email))
        {
            mBinding.email.setError("please enter pharmaceutical name ");
        }
        else if (TextUtils.isEmpty(password))
        {
            mBinding.password.setError("please enter pharmaceutical password ");
        }
        else {
             User p=new User(userName,email,"","pharma","");
             createPharmaAccount(p,password);
         }

    }

    private void createPharmaAccount(User p, String password)
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(p.getEmail(),password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    p.setId(task.getResult().getUser().getUid().toString());
                    addToDatabase(p);
                }
            }
        });

    }
    private void addToDatabase(User p)
    {
        database.child(p.getId()).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    successSweetDialog();
                }
            }
        });
    }
    private void successSweetDialog()
    {
        SweetAlertDialog success = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText("add pharmaceutical successfully ");
        success.show();
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