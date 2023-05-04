package com.example.dose.pharmaceutical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.dose.R;
import com.example.dose.TreatmentFragment;
import com.example.dose.UserAccess.Login;
import com.example.dose.databinding.ActivityPharmaceuticalMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class PharmaceuticalMainActivity extends AppCompatActivity {
    private ActivityPharmaceuticalMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityPharmaceuticalMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.pharma, new DisplayUsers()).commit();
        bottomNavigationAction();

    }

    private void bottomNavigationAction() {
        mBinding.pharmaNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(PharmaceuticalMainActivity.this, Login.class));
                }
                else if (item.getItemId() == R.id.chat)
                    moveToFragment(new DisplayUsers());
                else if (item.getItemId() == R.id.check) {
                    Bundle b=new Bundle();
                    b.putString("type","1");
                    TreatmentFragment t=new TreatmentFragment();
                    t.setArguments(b);
                    moveToFragment(t);
                }
                return false;
            }
        });

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.pharma, fragment).addToBackStack(null).commit();
    }
}