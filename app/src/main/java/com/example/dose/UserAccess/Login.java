package com.example.dose.UserAccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.dose.Admin.AdminMainActivity;
import com.example.dose.R;
import com.example.dose.SweetDialog;
import com.example.dose.User.UserMainActivity;
import com.example.dose.databinding.ActivityLoginBinding;
import com.example.dose.pharmaceutical.PharmaceuticalMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
   private ActivityLoginBinding mBinding;
    private int pStatus=0;
    private DatabaseReference database;
    private SweetAlertDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        database= FirebaseDatabase.getInstance().getReference();
        login();
        showPassword();
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
             String password=mBinding.password.getText().toString();
             String type=mBinding.type.getSelectedItem().toString();

             if (email.equals("admin@gmail.com"))
             {
                 startActivity(new Intent(Login.this, AdminMainActivity.class));
             }
             else if (TextUtils.isEmpty(email))
             {
                 mBinding.email.setError("please enter your email");
             }
             else if (TextUtils.isEmpty(password))
             {
                 mBinding.password.setError("please enter your password");
             }
             else {
                 startLoading();
                 signIn(email,password);
             }
         }
     });
    }

    private void signIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String type=mBinding.type.getSelectedItem().toString();
                    if (type.equals("User")) {
                        isUser(task.getResult().getUser().getUid().toString());
                    }
                    else if (type.equals("Pharmaceutical")) {
                        isPharma(task.getResult().getUser().getUid().toString());
                    }else {
                        loading.dismiss();
                        funLoginFailed();
                    }

                }
                else {
                    loading.dismiss();
                    funLoginFailed();
                }
            }
        });
    }

    private void isPharma(String id) {
        database.child("pharma").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    loading.dismiss();
                    successSweetDialog();
                    startActivity(new Intent(Login.this, PharmaceuticalMainActivity.class));
                }
                else{
                    loading.dismiss();
                    funLoginFailed();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isUser(String id) {
        database.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    loading.dismiss();
                    successSweetDialog();
                    startActivity(new Intent(Login.this, UserMainActivity.class));
                }
                else{
                    loading.dismiss();
                    funLoginFailed();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void startLoading()
    {
        loading= SweetDialog.loading(this);
        loading.show();
    }
    private void successSweetDialog()
    {
        SweetAlertDialog success = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText("login successfully ");
        success.show();
    }
    private void funLoginFailed()
    {
        FirebaseAuth.getInstance().signOut();
        SweetAlertDialog field= SweetDialog.failed(this,"failed to login please try again ");
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                field.dismiss();
            }
        });
    }
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pStatus==0)
                {
                    Glide.with(Login.this)
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(Login.this)
                            .load(R.drawable.baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=0;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}