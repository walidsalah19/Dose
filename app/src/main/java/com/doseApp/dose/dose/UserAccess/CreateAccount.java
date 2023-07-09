package com.doseApp.dose.dose.UserAccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.doseApp.dose.dose.User.UserMainActivity;
import com.doseApp.dose.dose.Models.User;
import com.doseApp.dose.dose.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateAccount extends AppCompatActivity {
    private ActivityCreateAccountBinding mBinding;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        haveAccount();
        initFirebase();
        clickLogin();


    }
    private void initFirebase()
    {
        database= FirebaseDatabase.getInstance().getReference("users");
    }
    private void clickLogin()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserData();
            }
        });
    }

    private void checkUserData() {
        String email=mBinding.email.getText().toString();
        String password=mBinding.password.getText().toString();
        String userName=mBinding.username.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            mBinding.username.setError("please enter user name ");
        }
        else if (TextUtils.isEmpty(email))
        {
            mBinding.email.setError("please enter user name ");
        }
        else if (TextUtils.isEmpty(password))
        {
            mBinding.password.setError("please enter user password ");
        }
        else {
            User p=new User(userName,email,"","user","");
            createUserAccount(p,password);
        }

    }

    private void createUserAccount(User p, String password)
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
                    startActivity(new Intent(CreateAccount.this, UserMainActivity.class));
                    successSweetDialog();
                }
            }
        });
    }
    private void successSweetDialog()
    {
        SweetAlertDialog success = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText("Create Account successfully ");
        success.show();
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