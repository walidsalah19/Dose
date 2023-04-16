package com.example.dose.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dose.R;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.ActivityUserMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class UserMainActivity extends AppCompatActivity {
    private ActivityUserMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityUserMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, new UserHome()).commit();

        bottomNavigationAction();

    }

    private void bottomNavigationAction() {
        mBinding.doctorNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(UserMainActivity.this, Login.class));
                } else if (item.getItemId() == R.id.home)
                    moveToFragment(new UserHome());
                else if (item.getItemId() == R.id.profile)
                    moveToFragment(new UserProfile());
                return false;
            }
        });

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.userFrameLayout, fragment).addToBackStack(null).commit();
    }
}