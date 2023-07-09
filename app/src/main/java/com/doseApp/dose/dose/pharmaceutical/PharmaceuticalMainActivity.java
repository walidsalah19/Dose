package com.doseApp.dose.dose.pharmaceutical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.doseApp.dose.dose.R;
import com.doseApp.dose.dose.databinding.ActivityPharmaceuticalMainBinding;
import com.doseApp.dose.dose.UserAccess.Login;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class PharmaceuticalMainActivity extends AppCompatActivity {
    private ActivityPharmaceuticalMainBinding mBinding;
    private String userToken;
    private Map<String ,String> profile=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityPharmaceuticalMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.pharma, new DisplayUsers()).commit();
        bottomNavigationAction();
        updateToken();

    }
    private void updateToken()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    userToken = task.getResult();
                    System.out.println(userToken);
                    profile.put("token",userToken);
                    //database.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(profile);
                    FirebaseDatabase.getInstance().getReference("tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(userToken);
                });
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
                    PharmaChack t=new PharmaChack();
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