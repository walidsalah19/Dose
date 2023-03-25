package com.example.dose.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dose.AddTreatment;
import com.example.dose.R;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.ActivityDoctorMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorMainActivity extends AppCompatActivity {
    private ActivityDoctorMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDoctorMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.doctor, new ManageArticles()).commit();
        bottomNavigationAction();

    }

    private void bottomNavigationAction() {
        mBinding.doctorNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(DoctorMainActivity.this, Login.class));
                } else if (item.getItemId() == R.id.addDose)
                    moveToFragment(new AddTreatment());
                else if (item.getItemId() == R.id.manageArticle)
                    moveToFragment(new ManageArticles());
                return false;
            }
        });

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.doctor, fragment).addToBackStack(null).commit();
    }
}